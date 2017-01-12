//
//  ScanCodeResultViewController.h
//  EMeeting
//
//  Created by efutureinfo on 16/5/10.
//  Copyright © 2016年 itp. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef void(^StartScanBlock)();
@interface ScanCodeResultViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIImageView *resultImageView;
@property (weak, nonatomic) IBOutlet UILabel *scanCodeTime;
@property (weak, nonatomic) IBOutlet UILabel *scanCodePersonnel;
@property (assign, nonatomic) NSInteger scanCodeNum;
@property (copy, nonatomic) NSString *showContent;
@property (assign, nonatomic) BOOL isSuccess;
@property (copy, nonatomic)  StartScanBlock startScanBlock;
@end
