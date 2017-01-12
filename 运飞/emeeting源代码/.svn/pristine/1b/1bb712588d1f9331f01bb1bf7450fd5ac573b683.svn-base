//
//  StroeyView.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "StroeyView.h"
#import "Tools.h"
#import "FMDBManager.h"
#import "MeetingRoomModel.h"

@interface StroeyView ()

@property (nonatomic, strong) NSMutableArray<UIButton *> *selectedButtonArr;

@property (nonatomic, strong) NSMutableArray<MeetingRoomModel *> *dataArr;

@property (nonatomic, copy) NSString *pid;

@property (nonatomic, copy) NSString *addressIds;

@end

@implementation StroeyView

- (instancetype)initWithFrame:(CGRect)frame andPid:(NSString *)pid andSelectAddressIds:(NSString *)addressIds
{
    if (self = [super initWithFrame:frame])
    {
        _selectedButtonArr = [[NSMutableArray alloc] init];
        
        _addressIds = addressIds;
        
        _pid = pid;
        
        [self loadDataArr];
        
        [self loadButtonSubView:frame andArr:self.dataArr];
    }
    return self;
}

- (void)loadDataArr
{
    _dataArr = [[NSMutableArray alloc] init];
    
    MeetingRoomModel *model = [[MeetingRoomModel alloc] init];
    model.addessChinese = @"不限";
    model.iD = _pid;
    
    [_dataArr addObject:model];
    
    NSArray *arr = [[FMDBManager shareInstance] selectSysMeetingRoomAddressInfoLevelId:@"4" andPID:_pid];
    
    for (MeetingRoomModel *model1 in [self loadMyDataWirtArr:arr])
    {
        [_dataArr addObject:model1];
    }
}

- (NSArray *)loadMyDataWirtArr:(NSArray *)arr
{
    NSMutableArray *p = [NSMutableArray arrayWithArray:arr];
    
    [p sortUsingComparator:^NSComparisonResult(id obj1, id obj2)
     {
         NSString *aName = [(MeetingRoomModel *)obj1 addessChinese];
         NSString *bName = [(MeetingRoomModel *)obj2 addessChinese];
         
         NSString *a = [aName substringToIndex:([aName length]-1)];
         NSString *b = [bName substringToIndex:([bName length]-1)];
         
         int aNum = [a intValue];
         int bNum = [b intValue];
         
         if (aNum > bNum) {
             return NSOrderedDescending;
         }
         else if (aNum < bNum){
             return NSOrderedAscending;
         }
         else {
             return NSOrderedSame;
         }
     }];
    
    return p;
}

- (void)loadButtonSubView:(CGRect)frame andArr:(NSMutableArray<MeetingRoomModel *> *)dataArr
{
    CGFloat btnW = 70;
    CGFloat btnH = 30;
    CGFloat btnY = 20;
    CGFloat btnX = (frame.size.width - 3*btnW)/2.0;
    
    int row = (int)dataArr.count/3;
    int number = 0;
    
    if (dataArr.count%3 > 0)
    {
        row = row +1;
        number = dataArr.count%3;
    }else
    {
        number = 3;
    }
    
    for (int i = 0; i<row; i++)
    {
        for (int j = 0; j<3; j++)
        {
            if (i==row-1 && j >= number)
            {
                break;
            }
            UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
            button.frame = CGRectMake(btnX + j*btnW, btnY + i*btnH, btnW, btnH);
            button.titleLabel.font = [UIFont systemFontOfSize:16];
            [button setTitle:[dataArr[i*3 + j] addessChinese] forState:UIControlStateNormal];
            [button setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
//            button.layer.borderWidth = 1;
//            button.layer.borderColor = RGBA(204, 204, 204, 1.0).CGColor;
            button.tag = SelectStroeyButtonBeginTag + i*3 + j;
            [button addTarget:self action:@selector(buttonClick:) forControlEvents:UIControlEventTouchUpInside];
            [self addSubview:button];
            
        }
    }
    
    
    for (int i = 0 ; i <= row; i++)
    {
        if (i < row && row!=1)
        {
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX, btnY+i*btnH, btnW * 3, 1)];
            line.backgroundColor = RGBA(204, 204, 204, 1.0);
            [self addSubview:line];
        }else
        {
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX, btnY+i*btnH, btnW * number+1, 1)];
            line.backgroundColor = RGBA(204, 204, 204, 1.0);
            [self addSubview:line];
        }
    }
    
    for (int i = 0; i < row; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            if (i==row-1 && j > number)
            {
                break;
            }
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX+j*btnW, (btnY+1)+i*btnH, 1, btnH -1)];
            line.backgroundColor = RGBA(204, 204, 204, 1.0);
            
            if (j == 3)
            {
                line.frame = CGRectMake(btnX+j*btnW-1, (btnY+1)+i*btnH, 1, btnH -1);
            }
            
            [self addSubview:line];
        }
    }

}

- (void)setButtonBackgroundColorFromTag:(NSInteger)fromtag ToTag:(NSInteger)totag andSelectTag:(NSInteger)selecttag
{
    for (NSInteger i = fromtag; i <= totag; i++)
    {
        
        UIButton *button = [self viewWithTag:i];
        button.backgroundColor = [UIColor clearColor];
        [button setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
        if (i == selecttag)
        {
            
            button.backgroundColor = RGBA(255, 150, 0, 1.0);
            [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        }
    }
}


- (void)buttonClick:(UIButton *)button
{
    if (button.tag == SelectStroeyButtonBeginTag)
    {
        [self setButtonBackgroundColorFromTag:SelectStroeyButtonBeginTag ToTag:SelectStroeyButtonBeginTag + self.dataArr.count-1 andSelectTag:SelectStroeyButtonBeginTag];
        [self.selectedButtonArr removeAllObjects];
    }else
    {
        [self removeFirstNotLimitButton];
        
        UIButton *button1 = [self viewWithTag:SelectStroeyButtonBeginTag];
        button1.backgroundColor = [UIColor clearColor];
        [button1 setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
        
        button.backgroundColor = RGBA(255, 150, 0, 1.0);
        [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    }
    
    if (![self.selectedButtonArr containsObject:button])
    {
        [self.selectedButtonArr addObject:button];
    }else
    {
        [self.selectedButtonArr removeObject:button];
        
        button.backgroundColor = [UIColor clearColor];
        [button setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
        
        if (self.selectedButtonArr.count == 0)
        {
             UIButton *notLimitButton = [self viewWithTag:SelectStroeyButtonBeginTag];
            notLimitButton.backgroundColor = RGBA(255, 150, 0, 1.0);
            [notLimitButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            [self.selectedButtonArr addObject:notLimitButton];
        }
    }
    
    [self loadmeetingRoomAddressIds];
    
    if ([self.delegate respondsToSelector:@selector(stroeyView:didSelectedStroeyArr:)])
    {
        [self.delegate stroeyView:self didSelectedStroeyArr:_meetingRoomAddressIds];
    }
}

- (void)loadmeetingRoomAddressIds
{
    NSMutableArray *idsArr = [[NSMutableArray alloc] init];
    
    for (UIButton *button in self.selectedButtonArr)
    {
        NSInteger index = button.tag - SelectStroeyButtonBeginTag;
        
        [idsArr addObject:[self.dataArr[index] iD]];
    }
    
    _meetingRoomAddressIds = [idsArr componentsJoinedByString:@","];
}

- (void)addFirstNotLimitButton
{
    UIButton *button = [self viewWithTag:SelectStroeyButtonBeginTag];
    
    [self buttonClick:button];
}

- (void)removeFirstNotLimitButton
{
    UIButton *button = [self viewWithTag:SelectStroeyButtonBeginTag];
    
    if ([self.selectedButtonArr containsObject:button])
    {
        [self.selectedButtonArr removeObject:button];
    }
}

- (void)setMeetingRoomAddressIds:(NSString *)meetingRoomAddressIds
{
    _meetingRoomAddressIds = meetingRoomAddressIds;

    if ([meetingRoomAddressIds isEqualToString:_pid])
    {
        [self addFirstNotLimitButton];
    }else
    {
        NSArray *meetingArr = [meetingRoomAddressIds componentsSeparatedByString:@","];
        
        for (int i = 0; i<self.dataArr.count; i++)
        {
            if ([meetingArr containsObject:[self.dataArr[i] iD]])
            {
                UIButton *button = [self viewWithTag:SelectStroeyButtonBeginTag+i];
                
                [self buttonClick:button];
            }
        }

    }
}

@end
