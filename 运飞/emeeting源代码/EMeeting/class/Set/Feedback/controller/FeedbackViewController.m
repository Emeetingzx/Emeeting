//
//  FeedbackViewController.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/2.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "FeedbackViewController.h"
#import "Tools.h"
#import "HelpFeedback.h"
#import "CustomAlertView.h"
#import "ProgressView.h"
@interface FeedbackViewController (){
    UITextField *selectText;
    HelpFeedback *helpFeedback;
}
@end

@implementation FeedbackViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    _proposalText.delegate=self;
    _phoneNumTextField.delegate=self;
    [self setKeyBoardNoti];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - 输入框输入限制条件
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    NSString *regex = @"";
    
    // 判断的字符串
    NSString * toBeString = [textField.text stringByReplacingCharactersInRange:range withString:string];
        regex = @"^[0-9]{0,11}$";
        // 创建谓词对象并设定条件的表达式
        NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", regex];
        // 对字符串进行判断
        if ([predicate evaluateWithObject:toBeString]) {
            return YES;
            
        }
    return NO;
}
#pragma mar 内容将要发生改变编辑

- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text{
    if (range.location>=500) {
        
        return NO;
    }
    return YES;
}

#pragma mark - 输入框输入限制条件
- (void)textViewDidChange:(UITextView *)textView {
    NSInteger number = [textView.text length];
    if (number > 500) {
        UIAlertView *tipAlert = [[UIAlertView alloc] initWithTitle:@"提示" message:@"字符个数不能大于500" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
        [tipAlert show];
        textView.text = [textView.text substringToIndex:500];
//        number = 10;
        
    }
}
#pragma mark - 监听键盘，添加键盘观察者
- (void)setKeyBoardNoti
{
    //监听键盘抬起事件
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(KeyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
    
    //监听键盘掉下的事件
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(KeyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
    
    //[self.proposalText addTarget:self action:@selector(:) forControlEvents:UIControlEventEditingChanged];
    
}

#pragma mark - 监听键盘，键盘出现
- (void)KeyboardWillShow:(NSNotification *)noti
{

    NSDictionary *dic = noti.userInfo;
    NSValue *keyboardValue = [dic objectForKey:@"UIKeyboardFrameEndUserInfoKey"];
    CGRect keyboardRect = keyboardValue.CGRectValue;
    
    NSNumber *duration = [noti.userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey];
    CGFloat offset = 356-(APPH - keyboardRect.size.height);
    __weak typeof(self) wself = self;
    if (offset >0)
    {
        if (selectText) {
            [UIView animateWithDuration:[duration floatValue] animations:^{
                _bgViewTopConstraint.constant=-offset;
                [wself.view layoutIfNeeded];
            }];

        }
    }
}

#pragma mark - 监听键盘，键盘消失
- (void)KeyboardWillHide:(NSNotification *)noti
{
    __weak typeof(self) wself = self;
    
    NSNumber *duration = [noti.userInfo objectForKey:UIKeyboardAnimationDurationUserInfoKey];
        [UIView animateWithDuration:[duration floatValue] animations:^{
            _bgViewTopConstraint.constant=0;
            [wself.view layoutIfNeeded];
        }];
}

#pragma mark 发送提交请求
-(void)requestHelpFeedback{
    if (!helpFeedback) {
        helpFeedback=[[HelpFeedback alloc]init];
    }
     __weak typeof(self) wself = self;
    [helpFeedback sendJSONRequestWithFeedbackInfo:wself.proposalText.text Success:^(ResponseObject *response) {
        if (response.s) {
            //[wself reserveComplete];
            wself.block();
            [wself back:nil];
            NSLog(@"=======");
        }else{
            CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:response.m ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:300];
            [wself.view addSubview:alertView];
            [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
        }

    } failure:^(NSError *error) {
        [ProgressView showHUDAddedTo:wself.view ProgressText:@"网络连接超时,请稍候重试！"];
    }];
    
}

#pragma mark 点击空白处关闭键盘
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    //隐藏键盘
    [self.phoneNumTextField resignFirstResponder];
    [self.proposalText resignFirstResponder];
}


-(void)textViewDidBeginEditing:(UITextView *)textView{
    selectText=nil;
}

#pragma mark - 开始编辑
- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    selectText = textField;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return YES;
}

#pragma mark 返回和取消
- (IBAction)back:(id)sender {
    
    [self.navigationController popViewControllerAnimated:YES];
    
}

#pragma mark 提交
- (IBAction)submitAction:(id)sender {
    if (_proposalText.text.length>0) {
        [self requestHelpFeedback];
    }else{
        CustomAlertView *alertView = [[CustomAlertView alloc] initWithFrame:[UIScreen mainScreen].bounds Message:@"意见不能为空" ImageName:nil andImageSize:CGSizeMake(0, 0) AlertWidth:150];
        [self.view addSubview:alertView];
        [alertView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.0];
    }
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
