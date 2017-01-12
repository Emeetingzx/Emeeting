//
//  ShakeScreenOutView.m
//  EMeeting
//
//  Created by efutureinfo on 16/2/16.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ShakeScreenOutView.h"
#import "MeetingRoomAddress.h"
#import "FMDBManager.h"

@interface ShakeScreenOutView ()

@property (nonatomic, strong) UIView *bgView;

@property (nonatomic, copy) NSString *location;

@property (nonatomic, strong) NSMutableArray<MeetingRoomAddress *> *placeArr;

@end

@implementation ShakeScreenOutView

- (instancetype)initWithFrame:(CGRect)frame meetingLocation:(NSString *)location subPlaceDataArr:(NSMutableArray *)placeArr
{
    if (self = [super initWithFrame:frame])
    {
        //_location = location;
        
        _placeArr = placeArr;
        
        self.backgroundColor = [UIColor colorWithWhite:0.0f alpha:0.5f];
        
//        self.selectedPlaceIds = _placeArr.firstObject.iD;
//        self.selectedTime = @"1";
        
        [self loadLocation];
        [self loadBgView];
    }
    
    return self;
}

- (void)loadLocation
{
    NSArray <MeetingRoomAddress *> *arr = [[FMDBManager shareInstance] selectSysMeetingRoomAddressInfoWithRoomId:_placeArr.firstObject.pID];
    self.location = arr.firstObject.addessChinese;
}

- (void)loadBgView
{
    self.bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 20, self.frame.size.width, 235 + (self.placeArr.count%3>0?self.placeArr.count/3+1:self.placeArr.count/3) * 30)];
    
    self.bgView.backgroundColor = [UIColor whiteColor];
    
    [self addSubview:self.bgView];
    
    [self loadLocationLabel];
    
    [self loadSelectPlaceButton];
    
    [self loadMeetingSelectTimeView];
    
    [self loadCancelAndConfirmButton];
    
    CGRect rect = self.bgView.frame;
    rect.origin.y = -rect.size.height;
    self.bgView.frame = rect;
    rect.origin.y = 20;
    __weak typeof(self) wself = self;
    [UIView animateWithDuration:0.2 animations:^
     {
         wself.bgView.frame = rect;
     }];
}

- (void)loadLocationLabel
{
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(10, 20, 100, 16)];
    label.font = [UIFont systemFontOfSize:16];
    label.textColor = [UIColor colorWithHexString:@"#333333"];
    label.text = @"会议地点";
    [self.bgView addSubview:label];
    
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(20, 55, 14, 14)];
    imageView.image = [UIImage imageNamed:@"service_place"];
    [self.bgView addSubview:imageView];
    
    UILabel *locationLabel = [[UILabel alloc] initWithFrame:CGRectMake(39, 55, APPW - 100, 14)];
    locationLabel.font = [UIFont systemFontOfSize:14];
    locationLabel.textColor = [UIColor colorWithHexString:@"#666666"];
    locationLabel.text = self.location;
    [self.bgView addSubview:locationLabel];
}

- (void)loadSelectPlaceButton
{
    if (self.placeArr.count == 0)
    {
        return;
    }
    
    CGFloat btnW = 106;
    CGFloat btnH = 30;
    CGFloat btnY = 80;
    CGFloat btnX = (APPW - 3*btnW)/2.0;
    
    int row = (int)self.placeArr.count/3;
    int number = 0;
    
    if (self.placeArr.count%3 > 0)
    {
        row = row +1;
        number = self.placeArr.count%3;
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
            [button setTitle:[self.placeArr[i*3 + j] addessChinese] forState:UIControlStateNormal];
            [button setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
//            button.layer.borderWidth = 1;
//            button.layer.borderColor = RGBA(204, 204, 204, 1.0).CGColor;
            button.tag = SelectPlaceButtonBeginTag + i*3 + j;
            [button addTarget:self action:@selector(selectPlaceButtonClick:) forControlEvents:UIControlEventTouchUpInside];
            [self.bgView addSubview:button];
            
            if (button.tag == SelectPlaceButtonBeginTag)
            {
                button.backgroundColor = RGBA(255, 150, 0, 1.0);
                [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
            }
        }
    }

    for (int i = 0 ; i <= row; i++)
    {
        if (i < row && row!=1)
        {
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX, btnY+i*btnH, btnW * 3, 1)];
            line.backgroundColor = RGBA(204, 204, 204, 1.0);
            [self.bgView addSubview:line];
        }else
        {
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX, btnY+i*btnH, btnW * number+1, 1)];
            line.backgroundColor = RGBA(204, 204, 204, 1.0);
            [self.bgView addSubview:line];
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
            
            [self.bgView addSubview:line];
        }
    }

    
}

- (void)loadMeetingSelectTimeView
{
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(10, 100+ (self.placeArr.count%3>0?self.placeArr.count/3+1:self.placeArr.count/3) * 30, 100, 20)];
    label.font = [UIFont systemFontOfSize:16];
    label.textColor = [UIColor colorWithHexString:@"#333333"];
    label.text = @"会议时间";
    [self.bgView addSubview:label];
    
    
    CGFloat btnW = 106;
    CGFloat btnH = 30;
    CGFloat btnY = label.frame.origin.y + 20 + 15;
    CGFloat btnX = (APPW - 3*btnW)/2.0;
    
    for (int i = 0; i<3; i++)
    {
        UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
        button.frame = CGRectMake(btnX + i*btnW, btnY, btnW, btnH);
        button.titleLabel.font = [UIFont systemFontOfSize:16];
        [button setTitle:[NSString stringWithFormat:@"%d小时",i+1] forState:UIControlStateNormal];
        [button setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
//        button.layer.borderWidth = 1;
//        button.layer.borderColor = RGBA(204, 204, 204, 1.0).CGColor;
        button.tag = SelectMeetingTimeBeginTag + i;
        [button addTarget:self action:@selector(selectTimeButtonClick:) forControlEvents:UIControlEventTouchUpInside];
        [self.bgView addSubview:button];
        
        if (button.tag == SelectMeetingTimeBeginTag)
        {
            button.backgroundColor = RGBA(255, 150, 0, 1.0);
            [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        }
    }
    
    for (int i = 0 ; i < 2; i++)
    {
        UIView *line = [[UIView alloc] initWithFrame:CGRectMake(btnX, btnY+i*btnH, btnW * 3, 1)];
        line.backgroundColor = RGBA(204, 204, 204, 1.0);
        [self.bgView addSubview:line];
    }
    
    for (int i = 0 ; i < 3; i++)
    {
        UIView *line1 = [[UIView alloc] initWithFrame:CGRectMake(btnX+i*btnW, btnY+1, 1, btnH-1)];
        line1.backgroundColor = RGBA(204, 204, 204, 1.0);
        [self.bgView addSubview:line1];
    }
    
    UIView *line2 = [[UIView alloc] initWithFrame:CGRectMake(btnX+3*btnW-1, btnY+1, 1, btnH-1)];
    line2.backgroundColor = RGBA(204, 204, 204, 1.0);
    [self.bgView addSubview:line2];

}

- (void)loadCancelAndConfirmButton
{
    UIButton *cancel = [UIButton buttonWithType:UIButtonTypeCustom];
    cancel.frame = CGRectMake(0,self.bgView.frame.size.height - 40,APPW/2.0,40);
    [cancel setTitle:@"取消" forState:UIControlStateNormal];
    [cancel setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    cancel.backgroundColor = [UIColor colorWithHexString:@"#CCCCCC"];
    [cancel addTarget:self action:@selector(cancel:) forControlEvents:UIControlEventTouchUpInside];
    [self.bgView addSubview:cancel];
    
    
    UIButton *confirm = [UIButton buttonWithType:UIButtonTypeCustom];
    confirm.frame = CGRectMake(APPW/2.0,self.bgView.frame.size.height - 40,APPW/2.0,40);
    [confirm setTitle:@"确定" forState:UIControlStateNormal];
    [confirm setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    confirm.backgroundColor = [UIColor colorWithHexString:@"#FF9600"];
    [confirm addTarget:self action:@selector(confirm:) forControlEvents:UIControlEventTouchUpInside];
    [self.bgView addSubview:confirm];

}

- (void)selectPlaceButtonClick:(UIButton *)button
{
    [self setButtonBackgroundColorFromTag:SelectPlaceButtonBeginTag ToTag:SelectPlaceButtonBeginTag+self.placeArr.count-1 andsupView:self.bgView andSelectTag:button.tag];
    
    if (self.placeArr.count>0)
    {
        _selectedPlaceIds = [self.placeArr[button.tag - SelectPlaceButtonBeginTag] iD];
    }
}

- (void)selectTimeButtonClick:(UIButton *)button
{
    [self setButtonBackgroundColorFromTag:SelectMeetingTimeBeginTag ToTag:SelectMeetingTimeEndTag andsupView:self.bgView andSelectTag:button.tag];
    
    _selectedTime = [NSString stringWithFormat:@"%li",button.tag - SelectMeetingTimeBeginTag + 1];
}

- (void)setButtonBackgroundColorFromTag:(NSInteger)fromtag ToTag:(NSInteger)totag andsupView:(UIView *)supView andSelectTag:(NSInteger)selecttag
{
    for (NSInteger i = fromtag; i<=totag; i++)
    {
        UIButton *button = [supView viewWithTag:i];
        button.backgroundColor = [UIColor clearColor];
        [button setTitleColor:RGBA(51, 51, 51, 1.0) forState:UIControlStateNormal];
        
        if (i == selecttag)
        {
            button.backgroundColor = RGBA(255, 150, 0, 1.0);
            [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        }
    }
}

- (void)cancel:(UIButton *)button
{
    [self removeFromSuperview];
}

- (void)confirm:(UIButton *)button
{
    if ([self.delegate respondsToSelector:@selector(shakeScreenOutView:didSelectedPlaceIds:selectedTime:)])
    {
        [self.delegate shakeScreenOutView:self didSelectedPlaceIds:self.selectedPlaceIds selectedTime:self.selectedTime];
    }
    
    [self removeFromSuperview];
}

- (void)setSelectedPlaceIds:(NSString *)selectedPlaceIds
{
    _selectedPlaceIds = selectedPlaceIds;
    
    int selectIndex = 0;
    
    for (int i = 0; i<self.placeArr.count; i++)
    {
        if ([selectedPlaceIds isEqualToString:[self.placeArr[i] iD]]){
            selectIndex = i;
        }
    }
    UIButton *button = [self.bgView viewWithTag:SelectPlaceButtonBeginTag+ selectIndex];
    [self selectPlaceButtonClick:button];
}

- (void)setSelectedTime:(NSString *)selectedTime
{
    _selectedTime = selectedTime;
    
    UIButton *button = [self.bgView viewWithTag:SelectMeetingTimeBeginTag+[selectedTime intValue]-1];
    [self selectTimeButtonClick:button];
}


@end
