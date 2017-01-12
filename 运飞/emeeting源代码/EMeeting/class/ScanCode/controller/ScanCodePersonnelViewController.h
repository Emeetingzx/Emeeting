//
//  ScanCodePersonnelViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/10.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeetingInfo.h"
@interface ScanCodePersonnelViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>
@property (weak, nonatomic) IBOutlet UIImageView *organizerImageView;
@property (weak, nonatomic) IBOutlet UILabel *organizerName;
@property (weak, nonatomic) IBOutlet UITableView *scanCodeTableView;
@property (weak, nonatomic) IBOutlet UIView *notStartView;
@property (strong, nonatomic) NSArray *alreadySignArr;
@property (strong, nonatomic) NSArray *noSignArr;
@property (strong, nonatomic) MeetingInfo *meetingInfo;
@property (strong, nonatomic) NSString *serverTimeString;
@end
