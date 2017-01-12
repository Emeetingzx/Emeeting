//
//  ContactsListTableViewCell.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/4.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ContactsListTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *headImage;
@property (weak, nonatomic) IBOutlet UILabel *name;
@property (weak, nonatomic) IBOutlet UILabel *phoneNum;
@property (weak, nonatomic) IBOutlet UIButton *invitationBtn;

@end
