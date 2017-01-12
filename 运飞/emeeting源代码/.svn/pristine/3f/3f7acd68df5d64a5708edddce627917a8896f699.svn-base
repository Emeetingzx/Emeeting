//
//  ScanCodeResultViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/10.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ScanCodeResultViewController.h"
#import "NSDate+LJFDate.h"
#import "NSDate+Extension.h"
@interface ScanCodeResultViewController ()

@end

@implementation ScanCodeResultViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    if (_isSuccess) {
        [NSDate getServerDate];
        NSString *scanCodeTimeString=[NSDate stringWithDate:[NSDate getServerDate] format:[NSDate ymdHmsFormat]];
        _scanCodeTime.text=[scanCodeTimeString substringWithRange:NSMakeRange(11,5)];
        _scanCodePersonnel.text=[NSString stringWithFormat:@"已有%@位参会人成功签到",_showContent];
    }else{
        _scanCodePersonnel.text=_showContent;
        _resultImageView.image=[UIImage imageNamed:@"signFail"];
        _scanCodeTime.hidden=YES;
    }
    
}

#pragma mark 返回
- (IBAction)back:(id)sender {
    self.startScanBlock();
    [self.navigationController popViewControllerAnimated:YES];
    
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
