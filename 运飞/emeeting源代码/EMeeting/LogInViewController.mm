//
//  LogInViewController.m
//  RedPacket
//
//  Created by itp on 15/10/27.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "LogInViewController.h"
#import "EMMSecurity.h"
#import "AppDelegate.h"

@interface LogInViewController ()

@property (weak, nonatomic) IBOutlet UITextField *userNameTextField;
@property (weak, nonatomic) IBOutlet UITextField *passwordTextField;
- (IBAction)logInAction:(id)sender;
@end

@implementation LogInViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.userNameTextField.text = [EMMSecurity share].userId;
    
}

- (void)didReceiveMemoryWarning
{
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

- (IBAction)logInAction:(id)sender
{
    
    [[EMMSecurity share] loginWithUserId:self.userNameTextField.text password:self.passwordTextField.text];
    [self performSegueWithIdentifier:@"home" sender:nil];
}


-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    AppDelegate *app=(AppDelegate *)[[UIApplication sharedApplication]delegate];
    [app jj];
}



@end
