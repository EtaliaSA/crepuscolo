package net.etalia.crepuscolo.services;

import java.util.List;
import java.util.Map;

import net.etalia.domain.Persistent;

public interface StorageService {

	public <T extends Persistent> T load(Class<T> clz, String id);

	public <T extends Persistent> T load(String query, Map<String, ?> criteria);

	public <T extends Persistent> T loadStored(Class<T> clz, String id);
	
	public <T extends Persistent> T save(T obj);

	public <T extends Persistent> T save(T obj, boolean andFlush);
	
	public <T extends Persistent> List<T> list(String query);

	public <T extends Persistent> List<T> list(String query, Map<String, ?> criteria);
	
	public <T extends Persistent> List<T> list(String query, Map<String, ?> criteria, Integer offset, Integer length);

	public <T extends Object> T rawQuery(String query, Map<String, ?> criteria);

	public List<? extends Object> rawQueryList(String query, Map<String, ?> criteria);

	public List<? extends Object> rawQueryList(String query, Map<String, ?> criteria, Integer offset, Integer length);
	
	public int rawUpdate(String query, Map<String, ?> criteria);

	public <T extends Object> T nativeQuery(String query, Map<String, ?> criteria);

	public List<? extends Object> nativeQueryList(String query, Map<String, ?> criteria);

	public void delete(Persistent obj);
	
	public <T extends Persistent> boolean delete(Class<T> clazz, String id);

	public boolean isPersisted(Persistent object);



}
