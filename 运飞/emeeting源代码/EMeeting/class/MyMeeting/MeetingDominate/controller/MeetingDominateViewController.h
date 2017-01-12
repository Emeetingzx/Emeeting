//
//  MeetingDominateViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "InvitationViewController.h"
@interface MeetingDominateViewController : UIViewController<UITableViewDelegate,UITableViewDataSource,UITextFieldDelegate>
@property (strong, nonatomic) NSArray *array;
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UITextField *numberTextField;
@property (copy, nonatomic) NSString *meetingId;
@property (copy, nonatomic) RefreshMeetingJoinInfoBlock refreshMeetingJoinInfoBlock;
@property(strong,nonatomic) NSArray *meetingJoinInfoList;
@end
