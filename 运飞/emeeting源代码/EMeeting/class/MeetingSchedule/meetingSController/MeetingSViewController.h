//
//  MeetingSViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/2/23.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

#define SetCenterControllerNoti @"SetCenterControllerNoti"

@interface MeetingSViewController : UIViewController

@property (weak, nonatomic) IBOutlet UITextField *nameTextFiled;

@property (weak, nonatomic) IBOutlet UILabel *leaderLabel;

@property (weak, nonatomic) IBOutlet UILabel *rankLabel;

@property (nonatomic, copy) NSString *meetingId;

@property (nonatomic, copy) NSString *leaderLevel;

@property (nonatomic, copy) NSString *memberNos;

@property (nonatomic, copy) NSString *meetingName;

@property (nonatomic, assign) BOOL isFromShake;

@property (nonatomic, assign) BOOL isFromFirst;

@end
