package net.etalia.crepuscolo.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import net.etalia.domain.EtaliaType;
import net.etalia.domain.ID;
import net.etalia.crepuscolo.domain.Entity;
import net.etalia.utils.Check;

public class CreationServiceImpl implements CreationService {

	private StorageService storageService = null;
	
	private Map<Class<?>,Constructor<?>> constructors = new HashMap<Class<?>, Constructor<?>>();

	
	public void setStorageService(StorageService storageService) {
		this.storageService = storageService;
	}
	
	@Override
	public <T extends Entity> T newInstance(Class<T> clazz) {
		@SuppressWarnings("unchecked")
		Constructor<T> constructor = (Constructor<T>) constructors.get(clazz);
		if (constructor == null) {
			try {
				constructor = clazz.getDeclaredConstructor();
				if (!Modifier.isPublic(constructor.getModifiers()))
					constructor.setAccessible(true);
			} catch (Exception e) {
				throw new IllegalStateException("Error while getting constructor for class " + clazz.getName(), e);
			}
			constructors.put(clazz, constructor);
		}
		try {
			return constructor.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Error instantiating class " + clazz.getName() + " using " + constructor.toGenericString(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> T getInstance(String id) {
		char determinant = id.charAt(0);
		EtaliaType type = EtaliaType.byChar(determinant);
		Check.illegalargument.assertNotNull("Unknown type for char " + determinant + " on id " + id, type);
		
		if (type == EtaliaType.publicationowner) {
			// TODO implement this load
		} else {
			storageService.load((Class<? extends Entity>)type.getDomainClass(), id);
		}
		return null;
	}

	@Override
	public void assignId(Entity obj) {
		obj.setId(ID.create(EtaliaType.byClass(obj.getClass())).toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Entity> T getEmptyInstance(String id) {
		char determinant = id.charAt(0);
		EtaliaType type = EtaliaType.byChar(determinant);
		Check.illegalargument.assertNotNull("Unknown type for char " + determinant + " on id " + id, type);
		T obj = newInstance((Class<T>)type.getDomainClass());
		obj.setId(id);
		return obj;
	}

}
