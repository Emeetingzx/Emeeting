//
//  ScanCodeViewController.m
//  EMeeting
//
//  Created by efutureinfo on 16/5/6.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ScanCodeViewController.h"
#import "UIViewController+MMDrawerController.h"
#import "Tools.h"
#import "UIColor+LJFColor.h"
#import "AttendanceOperation.h"
#import "ScanCodeResultViewController.h"
#import "ProgressView.h"
#import "MarkedWords.h"
@interface ScanCodeViewController (){
    int num;
    BOOL upOrdown;
    NSTimer * timer;
    AttendanceOperation *attendanceOperation;
    NSString *showContent;
}
@property (strong,nonatomic)AVCaptureDevice * device;
@property (strong,nonatomic)AVCaptureDeviceInput * input;
@property (strong,nonatomic)AVCaptureMetadataOutput * output;
@property (strong,nonatomic)AVCaptureSession * session;
@property (strong,nonatomic)AVCaptureVideoPreviewLayer * preview;
@end

@implementation ScanCodeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    upOrdown = NO;
    num =0;
    timer = [NSTimer scheduledTimerWithTimeInterval:.02 target:self selector:@selector(animation1) userInfo:nil repeats:YES];
}
-(void)animation1
{
    if (upOrdown == NO) {
        num ++;
        _lineImageViewcenterY.constant =2*num-120;
        if (2*num==240) {
            upOrdown = YES;
        }
    }
    else {
        num --;
        _lineImageViewcenterY.constant =2*num-120;
        if (num == 0) {
            upOrdown = NO;
        }
    }
    
}

#pragma mark AVCaptureMetadataOutputObjectsDelegate
- (void)captureOutput:(AVCaptureOutput *)captureOutput didOutputMetadataObjects:(NSArray *)metadataObjects fromConnection:(AVCaptureConnection *)connection
{
    AudioServicesPlaySystemSound(1007);
    NSString *stringValue;
    
    [_session stopRunning];
    [timer invalidate];
    timer = nil;
   
    if ( metadataObjects!=nil && [metadataObjects count] >0)
    {
        AVMetadataMachineReadableCodeObject * metadataObject = [metadataObjects objectAtIndex:0];
        stringValue = metadataObject.stringValue;
        NSLog(@"stringValue=%@",stringValue);
        [self requestAttendanceOperation:stringValue];
    }
}
-(void)requestAttendanceOperation:(NSString *)codeInfo{
    //NSArray *array = [codeInfo componentsSeparatedByString:@"\r"];
    //_maskView.hidden=NO;
    [ProgressView showCustomProgressViewAddTo:self.view];
    if (!attendanceOperation) {
        attendanceOperation=[[AttendanceOperation alloc]init];
    }
     __weak typeof(self) wself = self;
    [attendanceOperation sendJSONRequestWithMeetingId:nil CodeInfo:codeInfo Success:^(ResponseObject *response) {
        showContent=response.m;
        if (response.s) {
            [ProgressView hiddenCustomProgressViewAddTo:self.view];
            [_preview removeFromSuperlayer];
            _preview=nil;
            _session=nil;
            _output=nil;
            _input=nil;
            _device=nil;
            UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"ScanCode" bundle:nil];
            ScanCodeResultViewController  *scanCodeResultViewController = [storyboard instantiateViewControllerWithIdentifier:@"ScanCodeResultViewController"];

             scanCodeResultViewController.showContent=response.d;
            scanCodeResultViewController.isSuccess=YES;
            scanCodeResultViewController.startScanBlock=^{
                [wself startScan];
            };

            [wself.navigationController pushViewController:scanCodeResultViewController animated:YES];
            
            
        }else{
            [self performSelector:@selector(skipScanCodeResultViewController) withObject:nil afterDelay:2.0f];
        }
    } failure:^(NSError *error) {
        [ProgressView hiddenCustomProgressViewAddTo:self.view];
         [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
}

-(void)skipScanCodeResultViewController{
    [ProgressView hiddenCustomProgressViewAddTo:self.view];
    [_preview removeFromSuperlayer];
    _preview=nil;
    _session=nil;
    _output=nil;
    _input=nil;
    _device=nil;
    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"ScanCode" bundle:nil];
    ScanCodeResultViewController  *scanCodeResultViewController = [storyboard instantiateViewControllerWithIdentifier:@"ScanCodeResultViewController"];
     scanCodeResultViewController.showContent=showContent;
    __weak typeof(self) wself=self;
    scanCodeResultViewController.startScanBlock=^{
        [wself startScan];
    };
    scanCodeResultViewController.isSuccess=NO;
    [self.navigationController pushViewController:scanCodeResultViewController animated:YES];
}
- (IBAction)openLeftMenu:(id)sender
{
    [self.mm_drawerController toggleDrawerSide:MMDrawerSideLeft animated:YES completion:nil];
}
//-(void)viewWillAppear:(BOOL)animated
//{
//    [self setupCamera];
//}
- (void)setupCamera
{
    // Device
    _device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];
    
    // Input
    _input = [AVCaptureDeviceInput deviceInputWithDevice:self.device error:nil];
    
    // Output
    _output = [[AVCaptureMetadataOutput alloc]init];
    [_output setMetadataObjectsDelegate:self queue:dispatch_get_main_queue()];
    
    // Session
    _session = [[AVCaptureSession alloc]init];
    [_session setSessionPreset:AVCaptureSessionPresetHigh];
    if ([_session canAddInput:self.input])
    {
        [_session addInput:self.input];
    }
    
    if ([_session canAddOutput:self.output])
    {
        [_session addOutput:self.output];
    }
    
    // 条码类型 AVMetadataObjectTypeQRCode
    _output.metadataObjectTypes =@[AVMetadataObjectTypeQRCode,AVMetadataObjectTypeEAN13Code, AVMetadataObjectTypeEAN8Code, AVMetadataObjectTypeCode128Code];
    [_output setRectOfInterest : CGRectMake ((( APPH - 247 )/ 2.0)/ APPH ,(( APPW - 247 )/ 2.0)/ APPW , 247 / APPH , 247 / APPW )];
    // Preview
    _preview =[AVCaptureVideoPreviewLayer layerWithSession:self.session];
    _preview.videoGravity = AVLayerVideoGravityResizeAspectFill;
    //    _preview.frame =CGRectMake((sunW-280)/2,110,280,280);
    _preview.frame = CGRectMake(0, 0, APPW, APPH);
    [self.view.layer insertSublayer:self.preview atIndex:0];
    
    // Start
    [_session startRunning];
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self setupCamera];
}
-(void)startScan{
    timer = [NSTimer scheduledTimerWithTimeInterval:.02 target:self selector:@selector(animation1) userInfo:nil repeats:YES];
    //[self setupCamera];
    //[[self.preview connection] setEnabled:NO];
     //[self setupCamera];
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
    NSLog(@"OtherViewController-------viewDidDisappear");
    [timer invalidate];
    timer = nil;
    [_session stopRunning];
    _session=nil;
}


- (void)didReceiveMemoryiWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
    //[timer invalidate];
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
