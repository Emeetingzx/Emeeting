//
//  AddValueViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/17.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "AddValueViewController.h"
#import "MJRefresh.h"
#import "ProgressView.h"
#import "MarkedWords.h"
#import "GetUserIfAddValueServiceAdmin.h"
//没选择中的按钮字体颜色
#define defaultBtnColor  [UIColor colorWithRed:153/255.0 green:153/255.0 blue:153/255.0 alpha:1]

//没选择中的按钮背景颜色
#define defaultBgColor  [UIColor colorWithRed:244/255.0 green:244/255.0 blue:244/255.0 alpha:1]

//按钮选择字体颜色
#define selectedColor  [UIColor colorWithRed:1/255.0 green:174/255.0 blue:255/255.0 alpha:1]

#define selfViewWidth  self.view.frame.size.width
@interface AddValueViewController ()<AlertTableViewDelegate,CustomAlertViewDelegate>{
    UIScrollView *servicesReserveScrollView;
    GetFoodAndRefreshmentsInfos *getFoodAndRefreshmentsInfos;//获取食品茶点信息接口
    GetAddValueServiceRegionInfos *getAddValueServiceRegionInfos;//获取增值服务地区信息接口
    ReserveAddValueService *reserveAddValueService;//用户提交预定
    GetMyAddValueServiceInfos *getMyAddValueServiceInfos;//请求用户的增值服务数据接口
    AddValueServiceOperate  *addValueServiceOperate;//用户退订接口对象
    CGPoint sviewpoint;//纪录servicesReserveScrollView初始位置
    GetUserIfAddValueServiceAdmin *getUserIfAddValueServiceAdmin;//判断是否管理员接口对象
    MyAddValueInfo *unsubscribeMyAddValueInfo;
}

@end

@implementation AddValueViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self initialView];
    [self initialData];
   
}

#pragma mark —初始化视图
-(void)initialView{
    _scrollView.contentSize=CGSizeMake(APPW*2,0);
    _scrollView.tag=11000;
    _scrollView.delegate=self;
    _scrollView.scrollEnabled=YES;
    _scrollView.pagingEnabled=YES;
    _scrollView.showsVerticalScrollIndicator=NO;
    _scrollView.showsHorizontalScrollIndicator=NO;
    _myServicesTableView=[[UITableView alloc]initWithFrame:CGRectMake(APPW, 0, APPW,self.view.frame.size.height-_scrollView.frame.origin.y )];
    _myServicesTableView.delegate=self;
    _myServicesTableView.dataSource=self;
    _myServicesTableView.showsVerticalScrollIndicator=NO;
    _myServicesTableView.showsHorizontalScrollIndicator=NO;
    _myServicesTableView.tableFooterView=[[UIView alloc]init];
    [_myServicesTableView registerNib:[UINib nibWithNibName:@"MyServicesTableViewCell" bundle:nil] forCellReuseIdentifier:@"MyServicesTableViewCell"];
    [_scrollView addSubview:_myServicesTableView];
    
    servicesReserveScrollView=[[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, APPW,self.view.frame.size.height-_scrollView.frame.origin.y-48)];
    
    servicesReserveScrollView.delegate=self;
    servicesReserveScrollView.showsVerticalScrollIndicator=NO;
    servicesReserveScrollView.showsHorizontalScrollIndicator=NO;
    _reserveView= [[[NSBundle mainBundle]loadNibNamed:@"ReserveView" owner:nil options:nil]objectAtIndex:0];
    [self.reserveView setFrame:CGRectMake(0, 0, APPW,243)];
    __weak AddValueViewController *controller = self;
    _reserveView.block = ^void(){
        [controller loadTimeSelectView];
    };
    _reserveView.addressBlock=^void(){
        [controller regionAlertTableView];
    };
    UIButton *submitBtn=[[UIButton alloc]initWithFrame:CGRectMake(0,APPH-_scrollView.frame.origin.y-48, APPW, 48)];
    submitBtn.backgroundColor=RGBA(255,150,0,1);
    [submitBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [submitBtn addTarget:self action:@selector(requestReserveAddValueService) forControlEvents:UIControlEventTouchUpInside];
    [submitBtn setTitle:@"提  交" forState:UIControlStateNormal];
    [servicesReserveScrollView addSubview:self.reserveView];
    [_scrollView addSubview:submitBtn];
    [_scrollView addSubview:servicesReserveScrollView];
    [ProgressView showCustomProgressViewAddTo:_myServicesTableView];
    [self setupRefresh];
}

#pragma mark -初始化数据
-(void)initialData{
    self.foodInfoArr=[[NSMutableArray<FoodAndRefreshmentsInfo *> alloc]init];
    self.addValueRegionInfoArr=[[NSMutableArray<AddValueRegionInfo *> alloc]init];
    self.myAddValueInfoArr=[[NSMutableArray<MyAddValueInfo *> alloc]init];
    _p=[[PageRequestObject alloc]init];
    _isDown=NO;
    if (_valueErverNotifyType) {
        if ([@"1" isEqualToString:_valueErverNotifyType]) {
            _scrollView.contentOffset=CGPointMake(APPW,0 );
        }else{
            [self administratorsAction:nil];
        }
    }
    [self requestUserIfAddValueServiceAdmin];
    [self requestFoodAndRefreshmentsData];
    [self requestAddValueServiceRegionInfos];
    [self setKeyBoardNoti];
    [self requestMyAddValueServiceInfos];
    
}

#pragma mark 加载刷新视图
-(void)loadRefreshView:(UIView *) view{
    _touchRefreshView=[[TouchRefreshView alloc]initWithFrame:CGRectMake(0, (APPH-40)/2-116, APPW,40)];
    _touchRefreshView.title=@"很抱歉，没有符合条件的会议!";
    _touchRefreshView.hidden=YES;
    _touchRefreshView.tag=11000;
    _touchRefreshView.delegate=self;
    [self.myServicesTableView addSubview:_touchRefreshView];
}
#pragma mark 实现TouchRefreshView的代理
- (void)touchRefreshViewDidTouch:(TouchRefreshView *)touchView{
    [ProgressView showCustomProgressViewAddTo:_myServicesTableView];
    [self headerRereshing];
    
}

#pragma mark - 请求增值项目数据
-(void)requestFoodAndRefreshmentsData{
    if (!getFoodAndRefreshmentsInfos) {
        getFoodAndRefreshmentsInfos=[[GetFoodAndRefreshmentsInfos alloc]init];
    }
    __weak typeof(self) wself = self;
    [getFoodAndRefreshmentsInfos sendJSONRequestWithSuccess:^(ResponseObject *response) {
        for (NSDictionary *dic in response.d) {
            FoodAndRefreshmentsInfo *foodInfo=[[FoodAndRefreshmentsInfo alloc]initWithDictionary:dic];
            [wself.foodInfoArr addObject:foodInfo];
            
        }
        
        [wself loadfoodInfoData];
    } failure:^(NSError *error) {
        
    }];
}
#pragma mark - 是否管理员请求
-(void)requestUserIfAddValueServiceAdmin{
    if (!getUserIfAddValueServiceAdmin) {
        getUserIfAddValueServiceAdmin=[[GetUserIfAddValueServiceAdmin alloc]init];
    }
    __weak typeof(self) wself = self;
    [getUserIfAddValueServiceAdmin sendJSONRequestWithSuccess:^(ResponseObject *response) {
        
        if (response.s) {
            if ([@"Y" isEqualToString:response.d[@"AdminLogo"]]) {
                wself.allServicesBtn.hidden=NO;
            }else{
                 wself.allServicesBtn.hidden=YES;
            }
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:@"数据加载失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
         [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark - 请求地区数据
-(void)requestAddValueServiceRegionInfos{
    if (!getAddValueServiceRegionInfos) {
        getAddValueServiceRegionInfos=[[GetAddValueServiceRegionInfos alloc]init];
    }
    __weak typeof(self) wself = self;
    [getAddValueServiceRegionInfos sendJSONRequestWithSuccess:^(ResponseObject *response) {
        if (response.s) {
            for (NSDictionary *dic in response.d) {
                AddValueRegionInfo *addValueRegionInfo=[[AddValueRegionInfo alloc]initWithDictionary:dic];
                [wself.addValueRegionInfoArr addObject:addValueRegionInfo];
            }
            wself.reserveView.regionLable.text=wself.addValueRegionInfoArr.lastObject.addValueServiceRegionName;
        }else{
            [ProgressView showHUDAddedTo:wself.view ProgressText:@"数据加载失败,请稍候重试！"];
        }
        
        
    } failure:^(NSError *error) {
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];

}
#pragma mark - 提交增值预定请求
-(void)requestReserveAddValueService{
    [ProgressView showCustomProgressViewAddTo:self.view];
    
    if (!reserveAddValueService) {
        reserveAddValueService=[[ReserveAddValueService alloc]init];
    }
    __weak typeof(self) wself = self;
    ServiceReserveData *serviceReserveData=[[ServiceReserveData alloc]init];
    serviceReserveData.serviceAddress=wself.reserveView.detailedAddress.text;
    serviceReserveData.serviceDate=wself.reserveView.timeBtn.titleLabel.text;
    for (AddValueRegionInfo *regionInfo in self.addValueRegionInfoArr) {
        if ([regionInfo.addValueServiceRegionName isEqualToString:self.reserveView.regionLable.text]) {
            serviceReserveData.serviceRegionID=regionInfo.addValueServiceRegionId;
        }
        
    }
    
    serviceReserveData.phone=wself.reserveView.iphoneNum.text;
    NSString *foodIds=[[NSString alloc]init];
    
    for (int i=0;i<wself.reserveView.selectFoodInfoArr.count;i++) {
        if (i==0) {
            foodIds=[foodIds stringByAppendingFormat:@"%@",wself.reserveView.selectFoodInfoArr[i].addValueServiceId];
        }else{
            foodIds=[foodIds stringByAppendingFormat:@",%@",wself.reserveView.selectFoodInfoArr[i].addValueServiceId];
        }
    }
    serviceReserveData.foodIds=foodIds;
    
    
    [reserveAddValueService sendJSONRequestWithServiceReserveData:serviceReserveData Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        if (response.s) {
            [wself reserveComplete];
        }else{
            CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:response.m ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
            [wself.view addSubview:alertView];
            [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        }
        
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.view];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
       
    }];
    
}

-(void)reserveComplete{
    
    self.reserveView.iphoneNum.text=@"";
    self.reserveView.detailedAddress.text=@"";
    [self.reserveView.selectFoodInfoArr removeAllObjects];
    [self.reserveView calculationCurrentlyTime];
    [self requestFoodAndRefreshmentsData];
    CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:@"服务已成功提交，会议管理员会尽快和您联系!" firstButtonTitle:@"返回" secondButtonTitle:@"查看我的服务" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
    customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
    [self.view addSubview:customAlertView];
    [self headerRereshing];
}
#pragma mark －请求我的增值服务数据
-(void)requestMyAddValueServiceInfos{
    if (!getMyAddValueServiceInfos) {
        getMyAddValueServiceInfos=[[GetMyAddValueServiceInfos alloc]init];
    }
    __weak typeof(self) wself = self;
    [getMyAddValueServiceInfos sendJSONRequestWithPage:wself.p Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.myServicesTableView];
        [wself.myServicesTableView headerEndRefreshing];
        [wself.myServicesTableView footerEndRefreshing];
        if (response.s)
        {
            if (wself.isDown)
            {
                [wself.myAddValueInfoArr removeAllObjects];
                wself.isDown=NO;
                
            }
           
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSArray *arr=response.d;
                if (arr.count!=0) {
                    wself.p.pno++;
                    wself.p.e=response.p.e;
                    wself.p.t=[response.p.t intValue];
                    for (NSDictionary *dic in response.d) {
                        MyAddValueInfo *myAddValueInfo=[[MyAddValueInfo alloc]initWithDictionary:dic];
                        [wself.myAddValueInfoArr addObject:myAddValueInfo];
                    }
 
                }
            } else if (wself.myAddValueInfoArr.count!=0) {
                [MarkedWords showMarkedWordsWithMessage:@"没有更多数据" addToView:wself.view];
            }

            if (wself.myAddValueInfoArr.count==0) {
                wself.touchRefreshView.hidden=NO;
                [wself.myServicesTableView removeHeader];
                [wself.myServicesTableView removeFooter];
            }else{
                wself.touchRefreshView.hidden=YES;
                [wself setupRefresh];
            }
            [wself.myServicesTableView reloadData];
            
        }else{
           [ProgressView showHUDAddedTo:wself.view ProgressText:@"数据加载失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
}

#pragma mark －用户退订请求
-(void)requestaddValueServiceOperate:(NSString *)serviceOrderId{
    [ProgressView showCustomProgressViewAddTo:_myServicesTableView];
    if (!addValueServiceOperate) {
        addValueServiceOperate=[[AddValueServiceOperate alloc]init];
    }
    __weak typeof(self) wself = self;
    [addValueServiceOperate sendJSONRequestWithServiceOrderId:serviceOrderId OperateUserType:@"1" OperateType:@"1" Success:^(ResponseObject *response) {
         [ProgressView hiddenCustomProgressViewAddTo:wself.myServicesTableView];
        if (response.s) {
            [wself headerRereshing];
            [ProgressView showTextHUDAddedTo:wself.view ProgressText:@"退订成功！"];
        }else{
            [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
        }
    } failure:^(NSError *error) {
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

#pragma mark - 加载地区选择视图AlertTableView
-(void)regionAlertTableView{
    if (self.addValueRegionInfoArr.count>0) {
        NSMutableArray *arr=[[NSMutableArray alloc]init];
        for (AddValueRegionInfo *regionInfo in _addValueRegionInfoArr) {
            [arr addObject:regionInfo.addValueServiceRegionName];
        }
        [arr addObject:@"取消"];
        AlertTableView *type = [[AlertTableView alloc] initWithFrame:self.view.bounds andSelectedButtonString:self.reserveView.regionLable.text?:arr[0] andOtherButtons:arr andDelegate:self];
        [self.view addSubview:type];
    }
}
#pragma mark -  地区选择视图AlertTableView代理方法
- (void)AlertTableView:(AlertTableView *)alertTable andDidClickButtonIndex:(NSInteger)index andClickButtonIndexText:(NSString *)text{
    NSLog(@"%ld",(long)index);
    if (index!=10000) {
        self.reserveView.regionLable.text = text;
    }
    
    
}
#pragma mark —给foodInfoArr赋值
-(void)loadfoodInfoData{
    if (self.foodInfoArr.count>0) {
        self.reserveView.foodInfoArr=self.foodInfoArr;
        int rowNum=ceilf(self.foodInfoArr.count/3.0);
        servicesReserveScrollView.contentSize=CGSizeMake(0,243+rowNum*(APPW*2/9.0+10+20));
        [self.reserveView setFrame:CGRectMake(0, 0, APPW,243+rowNum*(APPW*2/9.0+10+20))];
    }

}
#pragma mark —scrollView代理方法
-(void)scrollViewDidScroll:(UIScrollView *)scrollView{
    if (_scrollView.tag==11000) {
        if (_scrollView.contentOffset.x==0)
        {
            [self selectedServiceReserve];
            
        }else if(_scrollView.contentOffset.x==self.view.frame.size.width)
        {
            [self selectedMyServices];
            
        }
    }
    
}

#pragma mark —CustomAlertView代理方法
- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index{
    if (customAlert.tag==1000001) {
         if (index==1) {
             [self requestaddValueServiceOperate:unsubscribeMyAddValueInfo.orderId];
         }
    }else{
        if (index==1) {
            [UIView animateWithDuration:0.5f animations:^{
                _scrollView.contentOffset=CGPointMake(APPW,0 );
            }];
            [self headerRereshing];
        }
 
    }
}
-(void)selectedMyServices{
    
    [UIView animateWithDuration:0.5f animations:^{
        _singViewLead.constant=selfViewWidth/2;
    }];
    [_serviceReserveBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _serviceReserveBtn.backgroundColor=defaultBgColor;
    
    [_myServicesBtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _myServicesBtn.backgroundColor=[UIColor whiteColor];
}

-(void)selectedServiceReserve{
    [UIView animateWithDuration:0.5f animations:^{
        _singViewLead.constant=0;
    }];
    
    [_myServicesBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _myServicesBtn.backgroundColor=defaultBgColor;
    
    [_serviceReserveBtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _serviceReserveBtn.backgroundColor=[UIColor whiteColor];
}
#pragma mark —tableView代理方法
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    
    return _myAddValueInfoArr.count;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
        MyServicesTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"MyServicesTableViewCell"];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        //tableView.rowHeight=113;
    [cell cellStyle:0];
    cell.serviceOrderNumberLable.text=_myAddValueInfoArr[indexPath.row].orderNumber;
    NSLog(@"1111%@",_myAddValueInfoArr[indexPath.row].orderState);
    tableView.rowHeight=[cell cellHeight:_myAddValueInfoArr[indexPath.row].serviceAddress];
    cell.serviceState.text=_myAddValueInfoArr[indexPath.row].orderState;
    cell.placeLable.text=_myAddValueInfoArr[indexPath.row].serviceAddress;
    cell.timeLable.text=_myAddValueInfoArr[indexPath.row].serviceDate;
    NSString *addValueString=@"";
    for (FoodAndRefreshmentsInfo *foodInfo in _myAddValueInfoArr[indexPath.row].foodAndRefreshmentsInfoArr) {
        addValueString=[addValueString stringByAppendingFormat:@"%@ ",foodInfo.addValueServiceName];
    }
    cell.addValueLable.text=addValueString;
    if ([ @"待处理" isEqualToString:_myAddValueInfoArr[indexPath.row].orderState]) {
        cell.rescindBtn.hidden=NO;
        [cell.rescindBtn addTarget:self action:@selector(unsubscribeAction:) forControlEvents:UIControlEventTouchUpInside];
    }else{
        cell.rescindBtn.hidden=YES;
    }
    
    cell.rescindBtn.tag=1010+indexPath.row;
    
    return cell;
}

#pragma mark 退订按钮事件
-(void)unsubscribeAction:(UIButton *)sender{
    unsubscribeMyAddValueInfo=_myAddValueInfoArr[sender.tag-1010];
    CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:@"确定退订该服务吗?" firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
    customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
    customAlertView.tag=1000001;
    [self.view addSubview:customAlertView];
    
}

#pragma mark 跳转管理员界面
- (IBAction)administratorsAction:(id)sender {
    
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"AddValue" bundle:nil];
    AdministratorsViewController  *administratorsViewController = [storyboard instantiateViewControllerWithIdentifier:@"AdministratorsViewController"];
    if (_valueErverNotifyType) {
        _valueErverNotifyType=nil;
        [self.navigationController pushViewController:administratorsViewController animated:NO];
    }else{
        [self.navigationController pushViewController:administratorsViewController animated:YES];
    }
    
}

#pragma mark - 加载时间选择视图TimeSelectView
- (void)loadTimeSelectView
{
    UIView *view = [[UIView alloc] initWithFrame:self.view.bounds];
    view.backgroundColor = [UIColor colorWithWhite:0 alpha:0.5];
    [self.view addSubview:view];
    
    ServeTimeSelectView *timeSelect = [[NSBundle mainBundle] loadNibNamed:@"ServeTimeSelectView" owner:self options:0][0];
    
    timeSelect.frame = CGRectMake(0, APPH - 200, APPW, 200);
    timeSelect.delegate=self;
    
    
    timeSelect.selectedTime = _reserveView.timeBtn.titleLabel.text;
    [view addSubview:timeSelect];
    
    CGRect rect = timeSelect.frame;
    rect.origin.y = APPH;
    timeSelect.frame = rect;
    rect.origin.y = APPH-rect.size.height;
    [UIView animateWithDuration:0.2 animations:^
     {
         timeSelect.frame = rect;
     }];
}

#pragma mark - 时间选择视图ServeTimeSelectView代理方法
- (void)serveTimeSelectViewDidCancel:(ServeTimeSelectView *)serveTimeSelec{
    
    CGRect rect = serveTimeSelec.frame;
    rect.origin.y = APPH;
    [UIView animateWithDuration:0.2 animations:^
     {
         serveTimeSelec.frame = rect;
     } completion:^(BOOL finished)
     {
         [serveTimeSelec.superview removeFromSuperview];
     }];

}

- (void)serveTimeSelectView:(TimeSelectView *)timeSelectView didSelectTime:(NSString *)selectedTimeString
{
    [self.reserveView.timeBtn setTitle:selectedTimeString forState:UIControlStateNormal];
    NSLog(@"selectedTimeString=%@",selectedTimeString);
    CGRect rect = timeSelectView.frame;
    rect.origin.y = APPH;
    [UIView animateWithDuration:0.2 animations:^
     {
         timeSelectView.frame = rect;
     } completion:^(BOOL finished)
     {
         [timeSelectView.superview removeFromSuperview];
     }];
}


#pragma mark - 监听键盘，添加键盘观察者
- (void)setKeyBoardNoti
{
    //监听键盘抬起事件
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(KeyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
    
    //监听键盘掉下的事件
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(KeyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
    
}

#pragma mark - 监听键盘，键盘出现
- (void)KeyboardWillShow:(NSNotification *)noti
{
    
    sviewpoint=servicesReserveScrollView.contentOffset;
    NSDictionary *dic = noti.userInfo;
    NSValue *keyboardValue = [dic objectForKey:@"UIKeyboardFrameEndUserInfoKey"];
    CGRect keyboardRect = keyboardValue.CGRectValue;
    
    NSNumber *duration = [noti.userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey];
    CGFloat offset = self.reserveView.selectText.frame.origin.y + 48+116 -sviewpoint.y - (APPH - keyboardRect.size.height);
    
    if (offset >0)
    {
        [UIView animateWithDuration:[duration floatValue] animations:^{
            servicesReserveScrollView.contentOffset=CGPointMake(0, offset+sviewpoint.y);
        }];
    }
}

#pragma mark - 监听键盘，键盘消失
- (void)KeyboardWillHide:(NSNotification *)noti
{
    NSNumber *duration = [noti.userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey];
    
    
    [UIView animateWithDuration:[duration floatValue] animations:^{
        servicesReserveScrollView.contentOffset=CGPointMake(0, sviewpoint.y);
    }];
}

#pragma mark  -集成刷新控件
- (void)setupRefresh
{
    // 1.下拉刷新(进入刷新状态就会调用self的headerRereshing)
    [_myServicesTableView addHeaderWithTarget:self action:@selector(headerRereshing)];
    // 自动刷新(一进入程序就下拉刷新)
    //[tableView1 headerBeginRefreshing];
    
    // 2.上拉加载更多(进入刷新状态就会调用self的footerRereshing)
    [_myServicesTableView addFooterWithTarget:self action:@selector(footerRereshing)];
    
    // 设置文字(也可以不设置,默认的文字在MJRefreshConst中修改)
    _myServicesTableView.headerPullToRefreshText = @"下拉可以刷新了";
    _myServicesTableView.headerReleaseToRefreshText = @"松开马上刷新了";
    _myServicesTableView.headerRefreshingText = @"刷新中...";
    
    _myServicesTableView.footerPullToRefreshText = @"上拉可以加载更多数据了";
    _myServicesTableView.footerReleaseToRefreshText = @"松开马上加载更多数据了";
    _myServicesTableView.footerRefreshingText = @"加载中...";
}

#pragma mark 开始进入刷新状态
- (void)headerRereshing
{
    // 1.刷新数据
    
    _isDown=YES;
    _p=[[PageRequestObject alloc]init];
    [self requestMyAddValueServiceInfos];
}

#pragma mark -加载下一页数据
- (void)footerRereshing
{
    // 1.加载下一页数据
    [self requestMyAddValueServiceInfos];
    
}

- (IBAction)openLeftMenu:(id)sender
{
    [self hideKeyBoard];
    [self.mm_drawerController toggleDrawerSide:MMDrawerSideLeft animated:YES completion:nil];
}

#pragma mark 我的会议和增值服务按钮绑定事件
- (IBAction)changeAction:(id)sender {
    if (sender==_myServicesBtn) {
        [self hideKeyBoard];
        _scrollView.contentOffset=CGPointMake(APPW,0 );
    }else{
         _scrollView.contentOffset=CGPointMake(0, 0);
        
    }
}

#pragma mark 关闭所有按钮
- (void)hideKeyBoard
{
    for (UIWindow* window in [UIApplication sharedApplication].windows)
    {
        for (UIView* view in window.subviews)
        {
            [self dismissAllKeyBoardInView:view];
        }
    }
}

-(BOOL) dismissAllKeyBoardInView:(UIView *)view
{
    if([view isFirstResponder])
    {
        [view resignFirstResponder];
        return YES;
    }
    for(UIView *subView in view.subviews)
    {
        if([self dismissAllKeyBoardInView:subView])
        {
            return YES;
        }
    }
    return NO;
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
