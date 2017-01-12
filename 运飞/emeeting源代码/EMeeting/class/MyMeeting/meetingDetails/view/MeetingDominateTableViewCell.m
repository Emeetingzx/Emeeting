//
//  MeetingDominateTableViewCell.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/4/26.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "MeetingDominateTableViewCell.h"
#import "UIColor+LJFColor.h"
#import "BaseNetworkingObject.h"
#import "Tools.h"
@implementation MeetingDominateTableViewCell

- (void)awakeFromNib {
    // Initialization code
}

-(CGFloat)cellHeightServiceAddress:(NSString *)serviceAddress{
    CGFloat cellHeight=[Tools heightOfString:serviceAddress font:[UIFont systemFontOfSize:17.0] width:APPW-80];
    return cellHeight-21+74;
}
-(void)setMeetingJoinInfo:(MeetingJoinInfo *)meetingJoinInfo{
    [_callBtn.layer removeAllAnimations];
    
    _endLineView.hidden=YES;
    _endImageView.hidden=YES;
    _endTime.hidden=YES;
    //meetingJoinInfo.endTime=@"22:55";
    if (meetingJoinInfo.endTime!=nil && ![@"" isEqualToString:meetingJoinInfo.endTime]) {
        _endLineView.hidden=NO;
        _endImageView.hidden=NO;
        _endTime.hidden=NO;
        _endTime.text=[NSString stringWithFormat:@"将在%@断开",meetingJoinInfo.endTime];
        _endTimeLableLeft.constant=30;
        
    }
    
    _boardroomAdress.text=meetingJoinInfo.showTerminalName;
    int i=[meetingJoinInfo.state intValue];
    _stateLableView.textColor=[UIColor colorWithHexString:@"1fea71"];
    //[_callBtn setExclusiveTouch:YES];
    //[_muteBtn setExclusiveTouch:YES];
    switch (i) {
        case 0://挂断
        {
            _lineView.hidden=YES;
            _muteStateImageView.hidden=YES;
            _stateLableView.text=@"待呼叫";
            _stateLableView.textColor=[UIColor colorWithHexString:@"a5a5a5"];
            _stateImageView.image=[UIImage imageNamed:@"notConnected"];
            [_callBtn.layer removeAnimationForKey:@"opacityAnimation1"];
            [_callBtn setImage:[UIImage imageNamed:@"shout"] forState:UIControlStateNormal];
             _callLable.text=@"呼叫";
            //[_callBtn setBackgroundImage:[UIImage imageNamed:@"shout"] forState:UIControlStateNormal];
            _callBtn.userInteractionEnabled = YES;
            _muteLable.text=@"禁言";
            _muteLable.alpha=0.5;
            _muteBtn.userInteractionEnabled = NO;
            _muteBtn.alpha=0.5;
            [_muteBtn setImage:[UIImage imageNamed:@"shutupBtn"] forState:UIControlStateNormal];
            _endLineView.hidden=YES;
            _endImageView.hidden=YES;
            _endTime.hidden=YES;
            _endLineViewLeft.constant=-1;
        } break;
        case 1://拨号中
        {
            _lineView.hidden=YES;
            _muteStateImageView.hidden=YES;
            _stateLableView.text=@"拨号中";
            [_callBtn.layer addAnimation:[self opacityForever_Animation:1.0] forKey:@"opacityAnimation1"];
            _stateImageView.image=[UIImage imageNamed:@"connected"];
            
            _callLable.text=@"呼叫";
            [_callBtn setImage:[UIImage imageNamed:@"shout"]forState:UIControlStateNormal];
             _muteLable.text=@"挂断";
             _muteBtn.alpha=1;
             _callBtn.userInteractionEnabled = NO;
             _muteBtn.userInteractionEnabled = YES;
            _muteLable.alpha=1;
            [_muteBtn setImage:[UIImage imageNamed:@"hangUp"] forState:UIControlStateNormal];
            _endLineViewLeft.constant=-1;
        }
            break;
        case 2://拨通中非静音状态
            _lineView.hidden=NO;
            _muteStateImageView.hidden=NO;
            _stateLableView.text=@"已连接";
            _muteStateImageView.image=[UIImage imageNamed:@"call"];
            _stateImageView.image=[UIImage imageNamed:@"connected"];
            [_callBtn.layer removeAnimationForKey:@"opacityAnimation1"];
            _callLable.text=@"挂断";
            [_callBtn setImage:[UIImage imageNamed:@"hangUp"] forState:UIControlStateNormal];
             _callBtn.userInteractionEnabled = YES;
            _muteLable.text=@"禁言";
            _muteLable.alpha=1;
            _muteBtn.alpha=1;
             _muteBtn.userInteractionEnabled = YES;
            [_muteBtn setImage:[UIImage imageNamed:@"shutupBtn"] forState:UIControlStateNormal];
            _endLineViewLeft.constant=28;
            if(APPW<375){
                _endImageView.hidden=YES;
                _endTimeLableLeft.constant=5;
            }
            break;
        case 3://拨通中静音状态
            _lineView.hidden=NO;
            _muteStateImageView.hidden=NO;
            _stateLableView.text=@"已连接";
            _muteStateImageView.image=[UIImage imageNamed:@"shutup"];
            _stateImageView.image=[UIImage imageNamed:@"connected"];
             _callLable.text=@"挂断";
            [_callBtn.layer removeAnimationForKey:@"opacityAnimation1"];
            [_callBtn setImage:[UIImage imageNamed:@"hangUp"] forState:UIControlStateNormal];
            _callBtn.userInteractionEnabled = YES;
             _muteLable.text=@"通话";
            _muteBtn.alpha=1;
            _muteLable.alpha=1;
            _muteBtn.userInteractionEnabled = YES;
            [_muteBtn setImage:[UIImage imageNamed:@"communicate"] forState:UIControlStateNormal];
            _endLineViewLeft.constant=28;
            if(APPW<375){
                _endImageView.hidden=YES;
                _endTimeLableLeft.constant=5;
            }
            break;
        default:
            break;
    }
    
}

#pragma mark === 永久闪烁的动画 ======
-(CABasicAnimation *)opacityForever_Animation:(float)time
{
    CABasicAnimation *animation = [CABasicAnimation
         animationWithKeyPath:@"opacity"];//必须写opacity才行。
    animation.fromValue = [NSNumber numberWithFloat:1.0f];
    animation.toValue = [NSNumber numberWithFloat:0.0f];//这是透明度。
    animation.autoreverses = YES;
    animation.duration = time;
    animation.repeatCount = MAXFLOAT;
    animation.removedOnCompletion = NO;
    animation.fillMode = kCAFillModeForwards;
    animation.timingFunction=[CAMediaTimingFunction
          functionWithName:kCAMediaTimingFunctionEaseIn];///没有的话是均匀的动画。
    return animation;
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
