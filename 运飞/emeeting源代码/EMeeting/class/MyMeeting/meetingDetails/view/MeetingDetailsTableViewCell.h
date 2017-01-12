//
//  MeetingDetailsTableViewCell.h
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/3.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MeetingDetailsTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *contentName;
@property (weak, nonatomic) IBOutlet UILabel *contents;
@property (weak, nonatomic) IBOutlet UIView *cuttingLine;
@property (weak, nonatomic) IBOutlet UIImageView *rightArrow;

@end
