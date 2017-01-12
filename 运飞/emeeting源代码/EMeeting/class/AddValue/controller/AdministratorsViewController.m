//
//  AdministratorsViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/18.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "AdministratorsViewController.h"
#import "MyServicesTableViewCell.h"
#import "MJRefresh.h"
#import "AddValueServiceOperate.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"
#import "MarkedWords.h"
#import "ProgressView.h"
//没选择中的按钮字体颜色
#define defaultBtnColor  [UIColor colorWithRed:153/255.0 green:153/255.0 blue:153/255.0 alpha:1]

//没选择中的按钮背景颜色
#define defaultBgColor  [UIColor colorWithRed:244/255.0 green:244/255.0 blue:244/255.0 alpha:1]

//按钮选择字体颜色
#define selectedColor  [UIColor colorWithRed:1/255.0 green:174/255.0 blue:255/255.0 alpha:1]

#define selfViewWidth  self.view.frame.size.width
@interface AdministratorsViewController (){
    GetAdminAddValueServiceInfos *getAdminAddValueServiceInfos;//管理员获取订单接口对象
    AddValueServiceOperate *addValueServiceOperate;//管理员操作订单接口对象
    AdminAddValueInfo *adminOperationInfo;//当前操作的订单
}

@end

@implementation AdministratorsViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    [self initialView];
    [self initialData];
    
}
#pragma mark 初始化视图
-(void)initialData{
    getAdminAddValueServiceInfos=[[GetAdminAddValueServiceInfos alloc]init];
    _currentTableView=(UITableView *)[_scrollView viewWithTag:10000];
    
    _newsOrderArr=[[NSMutableArray<AdminAddValueInfo *> alloc]init];
    _newsOrderPage=[[PageRequestObject alloc]init];
    _newsOrderisDown=NO;
    
    _receivedArr=[[NSMutableArray<AdminAddValueInfo *> alloc]init];
    _receivedPage=[[PageRequestObject alloc]init];
    _receivedisDown=NO;
    
    _finishArr=[[NSMutableArray<AdminAddValueInfo *> alloc]init];
    _finishPage=[[PageRequestObject alloc]init];
    _finishisDown=NO;
    
    addValueServiceOperate=[[AddValueServiceOperate alloc]init];

    [self requestNewsOrderData];
    [self requestReceivedData];
    [self requestfFinishData];
}

#pragma mark  只要滚动了就会触发
- (void)scrollViewDidScroll:(UIScrollView *)scrollView;
{
    if (scrollView.tag==11000) {
        if (_scrollView.contentOffset.x==0)
        {
            _currentTableView=(UITableView *)[_scrollView viewWithTag:10000];
            [self newsOrder];
            [self headerRereshing];
        }else if(_scrollView.contentOffset.x==self.view.frame.size.width)
        {
            _currentTableView=(UITableView *)[_scrollView viewWithTag:10001];
            [self receivedOrder];
             [self headerRereshing];
        }else if(_scrollView.contentOffset.x==self.view.frame.size.width*2)
        {
            _currentTableView=(UITableView *)[_scrollView viewWithTag:10002];
            [self finishOrder];
            [self headerRereshing];
        }
    }
    
}
#pragma mark 切换到新订单
-(void)newsOrder{
    
    [_newsOrderBtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _newsOrderBtn.backgroundColor=[UIColor clearColor];
    
    [_receivedBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _receivedBtn.backgroundColor=defaultBgColor;
    
    [_finishBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _finishBtn.backgroundColor=defaultBgColor;
    [UIView animateWithDuration:0.5f animations:^{
        _singViewLead.constant=0;
    }];
}
#pragma mark 切换到已接订单
-(void)receivedOrder{
    
    
    [_newsOrderBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _newsOrderBtn.backgroundColor=defaultBgColor;
    
    [_receivedBtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _receivedBtn.backgroundColor=[UIColor clearColor];
    
    [_finishBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _finishBtn.backgroundColor=defaultBgColor;
    [UIView animateWithDuration:0.5f animations:^{
        _singViewLead.constant=selfViewWidth/3;
    }];
    
}
#pragma mark 切换到完成订单
-(void)finishOrder{
    
    [_newsOrderBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _newsOrderBtn.backgroundColor=defaultBgColor;
    
    [_receivedBtn setTitleColor:defaultBtnColor forState:UIControlStateNormal];
    _receivedBtn.backgroundColor=defaultBgColor;
    
    [_finishBtn setTitleColor:selectedColor forState:UIControlStateNormal];
    _finishBtn.backgroundColor=[UIColor clearColor];
    [UIView animateWithDuration:0.5f animations:^{
        _singViewLead.constant=2*selfViewWidth/3;
    }];
    
}

#pragma mark 初始化视图
-(void)initialView{
    
    _scrollView.contentSize=CGSizeMake(selfViewWidth*3,0);
    _scrollView.tag=11000;
    _scrollView.delegate=self;
    _scrollView.scrollEnabled=YES;
    _scrollView.pagingEnabled=YES;
    for (int i=0; i<3; i++) {
        UITableView  *tabView=[[UITableView alloc]initWithFrame:CGRectMake(i*selfViewWidth,0, selfViewWidth,self.view.frame.size.height-_scrollView.frame.origin.y)];
        tabView.tag=10000+i;
        tabView.dataSource=self;
        tabView.delegate=self;
        tabView.tableFooterView=[[UIView alloc]init];
        
        [self loadRefreshView:tabView];
        [ProgressView showCustomProgressViewAddTo:tabView];
        [tabView registerNib:[UINib nibWithNibName:@"MyServicesTableViewCell" bundle:nil] forCellReuseIdentifier:@"MyServicesTableViewCell"];
        [_scrollView addSubview:tabView];
    }
    
}

#pragma mark 加载刷新视图
-(void)loadRefreshView:(UIView *) view{
    _touchRefreshView=[[TouchRefreshView alloc]initWithFrame:CGRectMake(0, (APPH-40)/2-116, APPW,40)];
    _touchRefreshView.title=@"很抱歉，没有符合条件的订单!";
    _touchRefreshView.hidden=YES;
    _touchRefreshView.tag=11000;
    _touchRefreshView.delegate=self;
    [view addSubview:_touchRefreshView];
}
#pragma mark 实现TouchRefreshView的代理
- (void)touchRefreshViewDidTouch:(TouchRefreshView *)touchView{
    [ProgressView showCustomProgressViewAddTo:_currentTableView];
    [self headerRereshing];
    
}
#pragma mark tableView代理
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    if (tableView.tag==10000) {
        return self.newsOrderArr.count;
    }else if(tableView.tag==10001){
        return self.receivedArr.count;
    }else{
        return self.finishArr.count;
    }

}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    MyServicesTableViewCell *cell=[tableView dequeueReusableCellWithIdentifier:@"MyServicesTableViewCell"];
    AdminAddValueInfo *adminAddValueInfo= nil;
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    if (tableView.tag==10000) {
        adminAddValueInfo=_newsOrderArr[indexPath.row];
        [cell cellStyle:2];
        [cell.orderTakingBtn setTitle:@"接单" forState:UIControlStateNormal];
    }else if(tableView.tag==10001){
        adminAddValueInfo=_receivedArr[indexPath.row];
       [cell cellStyle:2];
       [cell.orderTakingBtn setTitle:@"服务" forState:UIControlStateNormal];
    }else{
        adminAddValueInfo=_finishArr[indexPath.row];
        [cell cellStyle:1];
    }
    cell.serviceOrderNumberLable.text=adminAddValueInfo.orderNumber;
    cell.service_headlable.text=adminAddValueInfo.billOfLadingUser;
    cell.placeLable.text=adminAddValueInfo.serviceAddress;
    cell.timeLable.text=adminAddValueInfo.serviceDate;
    cell.serviceState.text=adminAddValueInfo.orderState;
    NSString *addValueString=@"";
    for (FoodAndRefreshmentsInfo *foodInfo in adminAddValueInfo.foodAndRefreshmentsInfoArr) {
        addValueString=[addValueString stringByAppendingFormat:@"%@ ",foodInfo.addValueServiceName];
    }
    cell.addValueLable.text=addValueString;
    [cell.rescindBtn addTarget:self action:@selector(unsubscribeAction:) forControlEvents:UIControlEventTouchUpInside];
    cell.rescindBtn.tag=1000000+indexPath.row;
    [cell.orderTakingBtn addTarget:self action:@selector(operationAction:) forControlEvents:UIControlEventTouchUpInside];
    cell.orderTakingBtn.tag=1100000+indexPath.row;
    tableView.rowHeight=[cell cellHeight1:adminAddValueInfo.serviceAddress];
    return cell;
}

#pragma mark 退订按钮绑定事件
-(void)unsubscribeAction:(UIButton *)sender
{
    NSLog(@"=====%ld",(long)sender.tag);
    if (self.currentTableView.tag==10000) {
        adminOperationInfo=_newsOrderArr[sender.tag-1000000];
    }else if(self.currentTableView.tag==10001){
        adminOperationInfo=_receivedArr[sender.tag-1000000];
    }
    
    CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:@"确定退订该服务吗?" firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
    customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
    customAlertView.tag=10000000;
    [self.view addSubview:customAlertView];
    
}

#pragma mark 接单和服务按钮绑定事件
-(void)operationAction:(UIButton *)sender
{
    NSLog(@"=====%ld",(long)sender.tag);
    if (self.currentTableView.tag==10000) {
        adminOperationInfo=_newsOrderArr[sender.tag-1100000];
    }else if(self.currentTableView.tag==10001){
        adminOperationInfo=_receivedArr[sender.tag-1100000];
    }
    NSString *message=@"确定接单吗?";
    if ([adminOperationInfo.actionItems rangeOfString:@"2"].location!=NSNotFound) {
        message=@"确定已服务吗?";
    }
    CustomAlertView *customAlertView=[[CustomAlertView alloc]initWithFrame:self.view.bounds delegate:self Message:message firstButtonTitle:@"取消" secondButtonTitle:@"确定" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:APPW>320?320:300];
    customAlertView.firstButton.backgroundColor=[UIColor colorWithHexString:@"CCCCCC"];
    //customAlertView.tag=10000000;
    [self.view addSubview:customAlertView];
    
}

#pragma mark —CustomAlertView代理方法
- (void)CustomAlertView:(CustomAlertView *)customAlert andButtonClickIndex:(NSInteger)index{
    if (index==1) {
        if (customAlert.tag==10000000) {
            [self requestaddValueOperateType:@"4"];
        }else{
            if ([adminOperationInfo.actionItems rangeOfString:@"2"].location!=NSNotFound) {
                [self requestaddValueOperateType:@"3"];
            }else{
                [self requestaddValueOperateType:@"2"];
            }
            
        }
    }
}

#pragma mark 新订单，已接订单，完成订单按钮绑定事件
- (IBAction)btnAction:(id)sender
{
    if (sender==_newsOrderBtn)
    {
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(0, 0);
        }];
        
    }else if (sender==_receivedBtn)
    {
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(selfViewWidth, 0);
        }];
    }else
    {
        [UIView animateWithDuration:0.5f animations:^{
            _scrollView.contentOffset=CGPointMake(selfViewWidth*2, 0);
        }];
    }
    
}
#pragma mark 集成刷新控件
- (void)setupRefresh:(UITableView *)tableView
{
    // 1.下拉刷新(进入刷新状态就会调用self的headerRereshing)
    [tableView addHeaderWithTarget:self action:@selector(headerRereshing)];
    //    // 自动刷新(一进入程序就下拉刷新)
    //[tableView headerBeginRefreshing];
    
    // 2.上拉加载更多(进入刷新状态就会调用self的footerRereshing)
    [tableView addFooterWithTarget:self action:@selector(footerRereshing)];
    
    // 设置文字(也可以不设置,默认的文字在MJRefreshConst中修改)
    tableView.headerPullToRefreshText = @"下拉可以刷新了";
    tableView.headerReleaseToRefreshText = @"松开马上刷新了";
    tableView.headerRefreshingText = @"刷新中...";
    
    tableView.footerPullToRefreshText = @"上拉可以加载更多数据了";
    tableView.footerReleaseToRefreshText = @"松开马上加载更多数据了";
    tableView.footerRefreshingText = @"加载中...";
}

#pragma mark 刷新数据
- (void)refreshData
{
        _newsOrderPage=[[PageRequestObject alloc]init];
        _newsOrderisDown=YES;
        [self requestNewsOrderData];
    
        _receivedPage=[[PageRequestObject alloc]init];
        _receivedisDown=YES;
        [self requestReceivedData];
        _finishPage=[[PageRequestObject alloc]init];
        _finishisDown=YES;
        [self requestfFinishData];
}



#pragma mark 开始进入刷新状态
- (void)headerRereshing
{
    // 1.刷新数据
    if (self.currentTableView.tag==10000) {
        
        _newsOrderPage=[[PageRequestObject alloc]init];
        _newsOrderisDown=YES;
        [self requestNewsOrderData];
        
    }else if(self.currentTableView.tag==10001){
        _receivedPage=[[PageRequestObject alloc]init];
        _receivedisDown=YES;
        [self requestReceivedData];
    }else{
        _finishPage=[[PageRequestObject alloc]init];
        _finishisDown=YES;
        [self requestfFinishData];
    }
}

#pragma mark -加载下一页数据
- (void)footerRereshing
{
    if (self.currentTableView.tag==10000) {
        [self requestNewsOrderData];
    }else if(self.currentTableView.tag==10001){
        [self requestReceivedData];
    }else{
        [self requestfFinishData];
    }
}

#pragma mark - 新订单数据请求
-(void)requestNewsOrderData{
    
    __weak typeof(self) wself = self;
    UITableView *tableView=[wself.scrollView viewWithTag:10000];
    [getAdminAddValueServiceInfos sendJSONRequestWithRelatedItemType:@"1" PageRequestObject:wself.newsOrderPage Success:^(ResponseObject *response) {
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        if (response.s) {
            if (wself.newsOrderisDown)
            {
                [wself.newsOrderArr removeAllObjects];
                wself.newsOrderisDown=NO;
                
            }
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSArray *arr=response.d;
                if (arr.count!=0) {
                    wself.newsOrderPage.pno++;
                    wself.newsOrderPage.e=response.p.e;
                    wself.newsOrderPage.t=[response.p.t intValue];
                    for (NSDictionary *dic in response.d) {
                        AdminAddValueInfo *adminAddValueInfo=[[AdminAddValueInfo alloc]initWithDictionary:dic];
                        [wself.newsOrderArr addObject:adminAddValueInfo];
                    }
                    
                }
                
            }else{
                if (wself.newsOrderArr.count!=0) {
                    [MarkedWords showMarkedWordsWithMessage:@"没有更多数据" addToView:wself.view];
                }
            }
            if (wself.newsOrderArr.count==0) {
                [tableView viewWithTag:11000].hidden=NO;
                [tableView removeHeader];
                [tableView removeFooter];
            }else{
                [tableView viewWithTag:11000].hidden=YES;
                [wself setupRefresh:tableView];
            }
            [tableView reloadData];
        }else{
            [ProgressView showHUDAddedTo:tableView ProgressText:@"数据加载失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [ProgressView showHUDAddedTo:tableView ProgressText:@"网络连接超时,请稍候重试！"];
        
    }];

}

#pragma mark - 新订单数据请求
-(void)requestReceivedData{
    
    __weak typeof(self) wself = self;
    UITableView *tableView=[wself.scrollView viewWithTag:10001];
    [getAdminAddValueServiceInfos sendJSONRequestWithRelatedItemType:@"2" PageRequestObject:wself.receivedPage Success:^(ResponseObject *response) {
        
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        if (response.s) {
            if (wself.receivedisDown)
            {
                [wself.receivedArr removeAllObjects];
                wself.receivedisDown=NO;
                
            }
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSArray *arr=response.d;
                if (arr.count!=0) {
                    wself.receivedPage.pno++;
                    wself.receivedPage.e=response.p.e;
                    wself.receivedPage.t=[response.p.t intValue];
                    for (NSDictionary *dic in response.d) {
                        AdminAddValueInfo *adminAddValueInfo=[[AdminAddValueInfo alloc]initWithDictionary:dic];
                        [wself.receivedArr addObject:adminAddValueInfo];
                    }
                    
                }
            }else{
                if (wself.receivedArr.count!=0) {
                    [MarkedWords showMarkedWordsWithMessage:@"没有更多数据" addToView:wself.view];
                }
            }

            if (wself.receivedArr.count==0) {
                [tableView viewWithTag:11000].hidden=NO;
                [tableView removeHeader];
                [tableView removeFooter];
            }else{
                [tableView viewWithTag:11000].hidden=YES;
                [wself setupRefresh:tableView];
            }
            [tableView reloadData];
        }else{
            [ProgressView showHUDAddedTo:tableView ProgressText:@"数据加载失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [ProgressView showHUDAddedTo:tableView ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
}

#pragma mark - 新订单数据请求
-(void)requestfFinishData{
    
    __weak typeof(self) wself = self;
     UITableView *tableView=[wself.scrollView viewWithTag:10002];
    [getAdminAddValueServiceInfos sendJSONRequestWithRelatedItemType:@"3" PageRequestObject:wself.finishPage Success:^(ResponseObject *response) {
       
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        if (response.s) {
            if (wself.finishisDown)
            {
                [wself.finishArr removeAllObjects];
                wself.finishisDown=NO;
                
            }
            
            if ([response.d isKindOfClass:[NSArray class]]) {
                NSArray *arr=response.d;
                if (arr.count!=0) {
                    wself.finishPage.pno++;
                    wself.finishPage.e=response.p.e;
                    wself.finishPage.t=[response.p.t intValue];
                    for (NSDictionary *dic in response.d) {
                        AdminAddValueInfo *adminAddValueInfo=[[AdminAddValueInfo alloc]initWithDictionary:dic];
                        [wself.finishArr addObject:adminAddValueInfo];
                    }
                    
                }
                
            }else{
                if (wself.finishArr.count!=0) {
                    [MarkedWords showMarkedWordsWithMessage:@"没有更多数据" addToView:wself.view];
                }
            }
            if (wself.finishArr.count==0) {
                [tableView viewWithTag:11000].hidden=NO;
                [tableView removeHeader];
                [tableView removeFooter];
            }else{
                [tableView viewWithTag:11000].hidden=YES;
                [wself setupRefresh:tableView];
            }
            [tableView reloadData];
        }else{
            [ProgressView showHUDAddedTo:tableView ProgressText:@"数据加载失败,请稍候重试！"];
        }
    } failure:^(NSError *error) {
        
        [tableView headerEndRefreshing];
        [tableView footerEndRefreshing];
        [ProgressView hiddenCustomProgressViewAddTo:tableView];
        [ProgressView showHUDAddedTo:tableView ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
}

#pragma mark －管理员操作增值服务
-(void)requestaddValueOperateType:(NSString *) operateType {
   
    __weak typeof(self) wself = self;
     [ProgressView showCustomProgressViewAddTo:wself.currentTableView];
    [addValueServiceOperate sendJSONRequestWithServiceOrderId:adminOperationInfo.orderId OperateUserType:@"2" OperateType:operateType Success:^(ResponseObject *response) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.currentTableView];
            if (response.s) {
                [wself refreshData];
                NSString *progressText=@"";
                if ([@"2"isEqualToString:operateType]) {
                    progressText=@"接单成功！";
                }else if ([@"3"isEqualToString:operateType]){
                    progressText=@"服务成功！";
                }else{
                    progressText=@"退单成功！";
                }
                [ProgressView showTextHUDAddedTo:wself.view ProgressText:progressText];
                
            }else{
                [MarkedWords showMarkedWordsWithMessage:response.m addToView:wself.view];
            }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:wself.currentTableView];
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

- (IBAction)back:(id)sender {
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
