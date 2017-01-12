//
//  AllMeetingViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "AllMeetingViewController.h"
#import "UserRelevantMeetingDateInfo.h"
#import "AllMeetingCollectionViewCell.h"
#import "AllMeetingCollectionReusableView.h"
#import "AllMeetingListViewController.h"
#import "Tools.h"
#import "GetUserRelevantMeetingDates.h"
#import "NSDate+RMCalendarLogic.h"
#import "NSDate+LJFDate.h"
#import "ProgressView.h"
@interface AllMeetingViewController (){
    GetUserRelevantMeetingDates *getUserRelevantMeetingDates;
    
}

@end

@implementation AllMeetingViewController
static NSString *allMeetingCollectionViewCell = @"AllMeetingCollectionViewCell";
static NSString *allMeetingCollectionReusableView = @"AllMeetingCollectionReusableView";
/**
 *  初始化模型数组对象
 */
- (NSMutableArray *)calendarMonth {
    if (!_calendarMonth) {
        _calendarMonth = [NSMutableArray array];
    }
    return _calendarMonth;
}

- (RMCalendarLogic *)calendarLogic {
    if (!_calendarLogic) {
        _calendarLogic = [[RMCalendarLogic alloc] init];
    }
    return _calendarLogic;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self initialView];
    [self initialData];
}

#pragma mark 初始化视图
-(void)initialView{
    _collectionView.delegate=self;
    _collectionView.dataSource=self;
    // 注册CollectionView的Cell
    [_collectionView registerNib:[UINib nibWithNibName:allMeetingCollectionViewCell bundle:nil] forCellWithReuseIdentifier:allMeetingCollectionViewCell];
    
    [_collectionView registerNib:[UINib nibWithNibName:allMeetingCollectionReusableView bundle:nil]forSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:allMeetingCollectionReusableView];
    if([[NSDate getServerDate] month]<10){
        _dateTitle.text=[NSString stringWithFormat:@"%lu年%0lu月",(unsigned long)[[NSDate getServerDate] year],(unsigned long)[[NSDate getServerDate] month]];
    }else{
        _dateTitle.text=[NSString stringWithFormat:@"%lu年%lu月",(unsigned long)[[NSDate getServerDate] year],(unsigned long)[[NSDate getServerDate] month]];
    }
    
    
    [self addPromptView];
    
}

#pragma mark 添加温馨提示视图
-(void)addPromptView{
    _promptView=[[UIView alloc]initWithFrame:CGRectMake(0, 116,APPW,20)];
    _promptView.backgroundColor=RGBA(255, 248, 210, 1);
    
    
    _promptLabel=[[UILabel alloc]initWithFrame:CGRectMake(15, 0,APPW-50,20)];
    _promptLabel.text=@"温馨提示：您共有0次会议!";
    _promptLabel.font=[UIFont systemFontOfSize:12.0f];
    _promptLabel.textColor=RGBA(51, 51, 51, 1);
    [_promptView addSubview:_promptLabel];
    
    UIButton *closeBtn=[[UIButton alloc]initWithFrame:CGRectMake(APPW-32, 0, 32, 20)];
    [closeBtn setImage:[UIImage imageNamed:@"allmeeting_close.png"] forState:UIControlStateNormal];
    [closeBtn addTarget:self action:@selector(hiddenPromptView) forControlEvents:UIControlEventTouchUpInside];
    [_promptView addSubview:closeBtn];
    [self.view addSubview:_promptView];
    _promptView.hidden=YES;
    
}
-(void)initialData{
        [self requestUserRelevantMeetingDates];
       self.calendarMonth = [self getMonthArrayOfDays:31 showType:CalendarShowTypeMultiple isEnable:NO modelArr:nil];
}
/**
 *  获取Days天数内的数组
 *
 *  @param days 天数
 *  @param type 显示类型
 *  @param arr  模型数组
 *  @return 数组
 */
- (NSMutableArray *)getMonthArrayOfDays:(int)days showType:(CalendarShowType)type isEnable:(BOOL)isEnable modelArr:(NSArray *)arr
{
    NSDate *date = [NSDate getServerDate];
    
    
    NSDate *selectdate  = [NSDate getServerDate];
    //返回数据模型数组
    return [self.calendarLogic reloadCalendarView:date selectDate:selectdate needDays:days showType:type priceModelArr:arr];
    
}

#pragma mark - CollectionView 数据源

// 返回组数
- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
    return self.calendarMonth.count;
}
// 返回每组行数
- (NSInteger)collectionView:(nonnull UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
    NSArray *arrary = [self.calendarMonth objectAtIndex:section];
    return arrary.count;
}

#pragma mark - CollectionView 代理

- (UICollectionViewCell *)collectionView:(nonnull UICollectionView *)collectionView cellForItemAtIndexPath:(nonnull NSIndexPath *)indexPath {
     AllMeetingCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:allMeetingCollectionViewCell forIndexPath:indexPath];
    NSArray *months = [self.calendarMonth objectAtIndex:indexPath.section];
    RMCalendarModel *model = [months objectAtIndex:indexPath.row];
    cell.model = model;
    return cell;
}


//定义每个UICollectionView 的大小
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath
{
    return CGSizeMake(self.view.frame.size.width/7, self.view.frame.size.width/7);
}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout referenceSizeForHeaderInSection:(NSInteger)section
{
    return CGSizeMake(self.view.frame.size.width, 20);
}


- (UICollectionReusableView *)collectionView:(UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath
{
    UICollectionReusableView *reusableview = nil;
    
    if (kind == UICollectionElementKindSectionHeader){
        
        NSMutableArray *month_Array = [self.calendarMonth objectAtIndex:indexPath.section];
         RMCalendarModel *model = [month_Array objectAtIndex:15];
        AllMeetingCollectionReusableView *monthHeader = [collectionView dequeueReusableSupplementaryViewOfKind:UICollectionElementKindSectionHeader withReuseIdentifier:allMeetingCollectionReusableView forIndexPath:indexPath];
         monthHeader.month.text = [NSString stringWithFormat:@"%lu月",(unsigned long)model.month];
        reusableview=monthHeader;
    }
    return reusableview;
    
}


- (void)collectionView:(nonnull UICollectionView *)collectionView didSelectItemAtIndexPath:(nonnull NSIndexPath *)indexPath {
    NSArray *months = [self.calendarMonth objectAtIndex:indexPath.section];
    RMCalendarModel *model = [months objectAtIndex:indexPath.row];
    if (model.isHaveMeeting) {
//        for (int i=0; i<self.calendarMonth.count; i++) {
//            for (int j=0; j<[self.calendarMonth[i] count];j++)
//            {
//                RMCalendarModel *model1 = [self.calendarMonth[i] objectAtIndex:j];
//                if (model1.style == CellDayTypeClick){
//                    model1.style=CellDayTypeFutur;
//                    [self.calendarMonth objectAtIndex:i][j]=model1;
//                }
//            }
//        }
//        if (model.style == CellDayTypeFutur ) {
//            model.style=CellDayTypeClick;
//            [self.calendarMonth objectAtIndex:indexPath.section][indexPath.row]=model;
//        }
        if (model.style!=CellDayTypeEmpty) {
            NSLog(@"%lu年%lu月%lu日",(unsigned long)model.year,(unsigned long)model.month,(unsigned long)model.day);
        
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"MyMeeting" bundle:nil];
            AllMeetingListViewController  *allMeetingListViewController = [storyboard instantiateViewControllerWithIdentifier:@"AllMeetingListViewController"];
            __weak typeof(self) wself = self;
            allMeetingListViewController.block=^{
                [wself requestUserRelevantMeetingDates];
            };
            allMeetingListViewController.dateModel=model;
            [self.navigationController pushViewController:allMeetingListViewController animated:YES];
        }
        [self.collectionView reloadData];
    }
    
}

- (BOOL)collectionView:(nonnull UICollectionView *)collectionView shouldSelectItemAtIndexPath:(nonnull NSIndexPath *)indexPath {
    return YES;
}


#pragma mark - 请求所有会议
-(void)requestUserRelevantMeetingDates{
    
    if (!getUserRelevantMeetingDates) {
        getUserRelevantMeetingDates=[[GetUserRelevantMeetingDates alloc]init];
    }
    __weak typeof(self) wself = self;
    [getUserRelevantMeetingDates sendJSONRequestWithSuccess:^(ResponseObject *response) {
        
        if (response.s) {
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                int meetingNum=0;
                for (NSDictionary  *dic in response.d) {
                    UserRelevantMeetingDateInfo *userRelevantMeetingDateInfo=[[UserRelevantMeetingDateInfo alloc]initWithDictionary:dic];
                    NSDate *date=[[NSDate alloc]init];
                    NSDateComponents *calendarMeeting = [[date dateFromString:userRelevantMeetingDateInfo.meetingsDate] YMDComponents];
                    [wself changStyle:calendarMeeting];
                    meetingNum+=[userRelevantMeetingDateInfo.theDayMettingNumber  intValue];
                }
                wself.promptLabel.text=[NSString stringWithFormat:@"温馨提示：您共有%d次会议!",meetingNum];
                [wself showPromptView];
                
            }
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:@"数据请求失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
}

-(void)changStyle:(NSDateComponents *) calendarMeeting{
    for (NSArray *month_Array in _calendarMonth) {
        for (RMCalendarModel *model in month_Array) {
            if (calendarMeeting.year == model.year & calendarMeeting.month == model.month & calendarMeeting.day == model.day)
            {
                model.isHaveMeeting=YES;
            }

        }
    }
    [self.collectionView reloadData];
}


-(void)showPromptView{
    self.promptView.hidden=NO;
    
    [self performSelector:@selector(hiddenPromptView) withObject:nil afterDelay:3];
    
}

-(void)hiddenPromptView{
    self.promptView.hidden=YES;
}

- (IBAction)back:(id)sender {
    //self.block();
    [self.navigationController popViewControllerAnimated:YES];
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
