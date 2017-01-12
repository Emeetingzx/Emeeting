//
//  ExtendView.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/5.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^ExtendBtnBlock)();
typedef void(^CancelBtnBlock)();
@interface ExtendView : UIView<UITableViewDelegate,UITableViewDataSource>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *tableViewHeight;
@property(nonatomic,copy) ExtendBtnBlock extendBtnBlock;
@property(nonatomic,copy) CancelBtnBlock cancelBtnBlock;
@property (nonatomic, strong) NSArray *meetingProLongList;
@end
