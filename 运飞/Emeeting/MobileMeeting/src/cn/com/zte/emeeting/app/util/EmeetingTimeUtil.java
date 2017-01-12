/**
 * 
 */
package cn.com.zte.emeeting.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 该类用于
 * @author wf
 */
public class EmeetingTimeUtil {

	
	
	public static void parseTimeToList(Map<Integer,List<TimeScale>> map,String startTime,String endTime)
	{
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date startDate = null;
		try {
			startDate = df.parse(startTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endDate = null;
		try {
			endDate = df.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cStart=Calendar.getInstance();
		cStart.setTime(startDate);
		Calendar cEnd=Calendar.getInstance();
		cEnd.setTime(endDate);
		
		int startHour=cStart.get(Calendar.HOUR_OF_DAY);
		int startM=cStart.get(Calendar.MINUTE);
		System.out.println(startHour);
		System.out.println("m:"+startM);
		int endHour=cEnd.get(Calendar.HOUR_OF_DAY);
		int endM=cEnd.get(Calendar.MINUTE);
		System.out.println(endHour);
		System.out.println("m:"+endM);
		System.out.println(endHour+","+startHour);
		
		
		//以下代码处理边缘情况
		if(startHour!=endHour)
		{
			if(startM>0)
			{
				float start = (float) (startM*1.0/60);
				float end = 1f;
				TimeScale scale = new TimeScale(start, end);
				putInMap(map,startHour, scale);
			}
			
			if(endM>0)
			{
				float end = (float) (endM*1.0/60);
				float start = 0f;
				TimeScale scale = new TimeScale(start, end);
				putInMap(map,endHour, scale);
			}
		}else
		{
			float start = (float) (startM*1.0/60);
			float end = (float) (endM*1.0/60);
			TimeScale scale = new TimeScale(start, end);
			putInMap(map,endHour, scale);
		}
		
		int i = 0;
		if(startM>0)
		{
			i=startHour+1;
		}else
		{
			i = startHour;
		}
		
		for(;i<endHour;i++)
		{
			putInMap(map,i, new TimeScale(0, 1));
		}
		
		System.out.println(map);
	}
	
	
	private static void putInMap(Map<Integer,List<TimeScale>> map,Integer key,TimeScale scale){
		if(map.get(key)==null)
		{
			List<TimeScale> list = new ArrayList<TimeScale>();
			list.add(scale);
			map.put(key, list);
		}else
		{
			map.get(key).add(scale);
		}
	}
	
	public static class TimeScale{
		float start;
		float end;
		/**
		 * @return the start
		 */
		public float getStart() {
			return start;
		}
		/**
		 * @param start the start to set
		 */
		public void setStart(float start) {
			this.start = start;
		}
		/**
		 * @return the end
		 */
		public float getEnd() {
			return end;
		}
		/**
		 * @param end the end to set
		 */
		public void setEnd(float end) {
			this.end = end;
		}
		public TimeScale(float start, float end) {
			super();
			this.start = start;
			this.end = end;
		}
		
		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "("+start+","+end+")";
		}
		
	}
}
