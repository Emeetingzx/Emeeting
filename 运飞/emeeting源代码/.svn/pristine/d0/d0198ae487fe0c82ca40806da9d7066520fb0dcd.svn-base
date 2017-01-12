//
//  RMCalendarLogic.m
//  RMCalendar
//
//  Created by 迟浩东 on 15/7/15.
//  Copyright © 2015年 迟浩东(http://www.ruanman.net). All rights reserved.
//

#import "RMCalendarLogic.h"
#import "NSDate+RMCalendarLogic.h"
#import "UserRelevantMeetingDateInfo.h"
@interface RMCalendarLogic()

/**
 *  今天的日期
 */
@property (nonatomic, strong) NSDate *today;
/**
 *  之后的日期
 */
@property (nonatomic, strong) NSDate *before;
/**
 *  选中的日期
 */
@property (nonatomic, strong) NSDate *select;
/**
 *  日期模型
 */
@property (nonatomic, strong) RMCalendarModel *model;
/**
 *  价格模型数组
 */
//@property (nonatomic, strong) NSArray *priceModelArr;

@end

@implementation RMCalendarLogic

// 初始化 模型数组，可根据功能进行修改
//- (NSArray *)priceModelArr {
//    if (!_priceModelArr) {
//        _priceModelArr = [NSArray array];
//    }
//    return _priceModelArr;
//}

-(NSMutableArray *)reloadCalendarView:(NSDate *)date selectDate:(NSDate *)selectDate needDays:(int)days showType:(CalendarShowType)type priceModelArr:(NSArray *)arr {
    // 存放会议模型
    //self.priceModelArr = arr;
    return [self reloadCalendarView:date selectDate:selectDate needDays:days showType:type];
}

- (NSMutableArray *)reloadCalendarView:(NSDate *)date selectDate:(NSDate *)selectDate needDays:(int)days showType:(CalendarShowType)type {
    //如果为空就从当天的日期开始
    if(date == nil){
        date = [NSDate date];
    }
    
    //默认选择中的时间
    if (selectDate == nil) {
        selectDate = date;
    }
    
    self.today = date;//起始日期
    
    self.before = [date dayInTheFollowingDay:days];//计算它days天以后的时间
    
    self.select = selectDate;//选择的日期
    
    NSDateComponents *todayDC= [self.today YMDComponents];
    
    NSDateComponents *beforeDC= [self.before YMDComponents];
    
    NSInteger todayYear = todayDC.year;
    
    NSInteger todayMonth = todayDC.month;
    
    NSInteger beforeYear = beforeDC.year;
    
    NSInteger beforeMonth = beforeDC.month;
    
    NSInteger months = (beforeYear-todayYear) * 12 + (beforeMonth - todayMonth);
    
    NSMutableArray *calendarMonth = [[NSMutableArray alloc]init];//每个月的dayModel数组
    
    if (type == CalendarShowTypeSingle) {
        months = 0;
    }
    
    for (int i = 0; i <= months; i++) {
        
        NSDate *month = [self.today dayInTheFollowingMonth:i];
        NSMutableArray *calendarDays = [[NSMutableArray alloc]init];
        [self calculateDaysInPreviousMonthWithDate:month andArray:calendarDays];
        [self calculateDaysInCurrentMonthWithDate:month andArray:calendarDays];
        if (type == CalendarShowTypeMultiple) {
            [self calculateDaysInFollowingMonthWithDate:month andArray:calendarDays];//计算下月份的天数
        }
        
//        [self calculateDaysIsWeekendandArray:calendarDays];
        
        [calendarMonth insertObject:calendarDays atIndex:i];
    }
    
    return calendarMonth;
}

//计算上月份的天数

- (NSMutableArray *)calculateDaysInPreviousMonthWithDate:(NSDate *)date andArray:(NSMutableArray *)array
{
    NSUInteger weeklyOrdinality = [[date firstDayOfCurrentMonth] weeklyOrdinality];//计算这个的第一天是礼拜几,并转为int型
    NSDate *dayInThePreviousMonth = [date dayInThePreviousMonth];//上一个月的NSDate对象
    int daysCount = (int)[dayInThePreviousMonth numberOfDaysInCurrentMonth];//计算上个月有多少天
    int partialDaysCount = (int)weeklyOrdinality - 1;//获取上月在这个月的日历上显示的天数
    NSDateComponents *components = [dayInThePreviousMonth YMDComponents];//获取年月日对象
    
    for (int i = daysCount - partialDaysCount + 1; i < daysCount + 1; ++i) {
        
        RMCalendarModel *calendarDay = [RMCalendarModel calendarWithYear:components.year month:components.month day:i];
        calendarDay.style = CellDayTypeEmpty;//不显示
        [array addObject:calendarDay];
    }
    
    
    return NULL;
}



//计算下月份的天数

- (void)calculateDaysInFollowingMonthWithDate:(NSDate *)date andArray:(NSMutableArray *)array
{
    NSUInteger weeklyOrdinality = [[date lastDayOfCurrentMonth] weeklyOrdinality];
    if (weeklyOrdinality == 7) return ;
    
    NSUInteger partialDaysCount = 7 - weeklyOrdinality;
    NSDateComponents *components = [[date dayInTheFollowingMonth] YMDComponents];
    
    for (int i = 1; i < partialDaysCount + 1; ++i) {
        RMCalendarModel *calendarDay = [RMCalendarModel calendarWithYear:components.year month:components.month day:i];
        calendarDay.style = CellDayTypeEmpty;
        [array addObject:calendarDay];
    }
}


//计算当月的天数

- (void)calculateDaysInCurrentMonthWithDate:(NSDate *)date andArray:(NSMutableArray *)array
{
    
    NSUInteger daysCount = [date numberOfDaysInCurrentMonth];//计算这个月有多少天
    NSDateComponents *components = [date YMDComponents];//今天日期的年月日
    
    for (int i = 1; i < daysCount + 1; ++i) {
        RMCalendarModel *calendarDay = [RMCalendarModel calendarWithYear:components.year month:components.month day:i];
        calendarDay.week = [[calendarDay date]getWeekIntValueWithDate];
        [self changStyle:calendarDay];
        [array addObject:calendarDay];
    }
}




- (void)changStyle:(RMCalendarModel *)model
{
    
    NSDateComponents *calendarSelect = [self.select YMDComponents];//默认选择的那一天
    
    //被点击选中
    if(calendarSelect.year == model.year &
       calendarSelect.month == model.month &
       calendarSelect.day == model.day){
        
        model.style = CellDayTypeClick;
        self.model = model;
    //没被点击选中
    }else{
        
        model.style = CellDayTypeFutur;
            
    }
////warning for进行模型日期匹配,将会议和日期关联，此处可根据项目需求进行修改
//    for (int i = 0; i < self.priceModelArr.count; i++) {
//        NSDateComponents *calendarMeeting = [self.priceModelArr[i] YMDComponents];
//        if (calendarMeeting.year == model.year &
//            calendarMeeting.month == model.month &
//            calendarMeeting.day == model.day) {
//            model.isHaveMeeting=YES;
//        }
//    }
//    
    
}
@end
