//
//  AlertTableView.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/3.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "AlertTableView.h"
#import "Tools.h"
#import "AlertTableCell.h"

@interface AlertTableView ()<UITableViewDataSource, UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;

@end

@implementation AlertTableView

-(instancetype)initWithFrame:(CGRect)frame andSelectedButtonString:(NSString *)selectedStr andOtherButtons:(NSArray<NSString *> *)dataArr andDelegate:(id)delegate
{
    if (self = [super initWithFrame:frame])
    {
        self.dataArr = dataArr;
        self.selectedButtonStr = selectedStr;
        self.delegate = delegate;
        [self loadTableView];
    }
    return self;
}

- (void)loadTableView
{
    self.backgroundColor = [UIColor colorWithWhite:0 alpha:0.5];
    
    self.tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, APPH - self.dataArr.count * 48, APPW, self.dataArr.count * 48) style:UITableViewStylePlain];
    self.tableView.delegate = self;
    self.tableView.dataSource = self;
    //self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.scrollEnabled = NO;
    
    [self addSubview:self.tableView];
    
    [self.tableView registerNib:[UINib nibWithNibName:@"AlertTableCell" bundle:nil] forCellReuseIdentifier:@"AlertTableCell"];
    
    
    CGRect rect = self.tableView.frame;
    rect.origin.y = APPH;
    self.tableView.frame = rect;
    rect.origin.y = APPH-rect.size.height;
    
    __weak typeof(self) wself = self;
    [UIView animateWithDuration:0.2 animations:^
    {
        wself.tableView.frame = rect;
    }];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataArr.count;
}


//返回Cell
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    AlertTableCell *cell = [tableView dequeueReusableCellWithIdentifier:@"AlertTableCell"];
    
    cell.nameLable.text = self.dataArr[indexPath.row];
    
    if ([self.dataArr[indexPath.row] isEqualToString:self.selectedButtonStr])
    {
        cell.nameLable.textColor = RGBA(1, 174, 255, 1.0);
        
    }else
    {
        cell.nameLable.textColor = RGBA(51, 51, 51, 1.0);
    }
    
    if (indexPath.row == self.dataArr.count-1)
    {
        cell.nameLable.textColor = [UIColor whiteColor];
        cell.backgroundColor = RGBA(204, 204, 204, 1.0);
    }else
    {
        cell.backgroundColor = [UIColor whiteColor];
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 48;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row == self.dataArr.count - 1)
    {
        [self.delegate AlertTableView:self andDidClickButtonIndex:CancelIndex andClickButtonIndexText:nil];
    }else
    {
        [self.delegate AlertTableView:self andDidClickButtonIndex:indexPath.row andClickButtonIndexText:self.dataArr[indexPath.row]];
    }
    
    CGRect rect = self.tableView.frame;
    rect.origin.y = APPH;
    
    __weak typeof(self) wself = self;
    [UIView animateWithDuration:0.2 animations:^
     {
         wself.tableView.frame = rect;
     } completion:^(BOOL finished)
     {
         [wself removeFromSuperview];
     }];
}


@end
