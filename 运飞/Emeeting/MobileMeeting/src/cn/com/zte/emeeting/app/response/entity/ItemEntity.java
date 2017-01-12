/**
 * 
 */
package cn.com.zte.emeeting.app.response.entity;

import java.util.List;
import java.util.Map;

import cn.com.zte.emeeting.app.util.EmeetingTimeUtil.TimeScale;

import android.view.View;


/**
 * 该类用于
 * @author wf
 */
public class ItemEntity {
	private String name;
	private Map<Integer,List<TimeScale>> timeScaleMap;
	
	
//	public ItemEntity(String name, List<View> viewList) {
//		super();
//		this.name = name;
//	}

	/**
	 * @return the map
	 */
	public Map<Integer, List<TimeScale>> getTimeScaleMap() {
		return timeScaleMap;
	}

	public ItemEntity(String name, Map<Integer, List<TimeScale>> map) {
		super();
		this.name = name;
		this.timeScaleMap = map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<Integer, List<TimeScale>> map) {
		this.timeScaleMap = map;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	
}
