//
//  ReserveView.m
//  EMeeting
//
//  Created by efutureinfo.cn on 16/2/19.
//  Copyright © 2016年 itp. All rights reserved.
//

#import "ReserveView.h"
#import "ReserveViewCellCollectionViewCell.h"
#import "Tools.h"
#import "FoodAndRefreshmentsInfo.h"
#import "GetFoodAndRefreshmentsInfos.h"
#import "UserImageCacheManager.h"

@implementation ReserveView{
         GetFoodAndRefreshmentsInfos *getFoodAndRefreshmentsInfos;
}

- (void)drawRect:(CGRect)rect {
    _collectionView.delegate=self;
    _collectionView.dataSource=self;
    _detailedAddress.delegate=self;
    
    _iphoneNum.delegate=self;
    [_collectionView registerNib:[UINib nibWithNibName:@"ReserveViewCellCollectionViewCell" bundle:nil] forCellWithReuseIdentifier:@"ReserveViewCellCollectionViewCell"];
    _selectFoodInfoArr=[[ NSMutableArray<FoodAndRefreshmentsInfo *> alloc]init];
    [_detailedAddress addTarget:self action:@selector(textFieldDidChange:) forControlEvents:UIControlEventEditingChanged];
    [self calculationCurrentlyTime];
}
#pragma mark 显示时间
-(void)calculationCurrentlyTime{
    
    NSDate *date=[NSDate getServerDate];
    NSLog(@"%@",date);
    NSInteger hour;
    hour=[date hour]+1;
    NSLog(@"hour=%ld",(long)hour);
    if (hour>=10) {
        _timeSring=[NSString stringWithFormat:@"%@ %ld:%@", [NSDate stringWithDate:date format:[date ymdFormat]],(long)hour,@"00"];
    }else{
         _timeSring=[NSString stringWithFormat:@"%@ 0%ld:%@", [NSDate stringWithDate:date format:[date ymdFormat]],(long)hour,@"00"];
    }
    [_timeBtn setTitle:_timeSring forState:UIControlStateNormal];
   // NSLog(@"llllllll=%@",timeSring);
    
}

-(void)setFoodInfoArr:(NSMutableArray<FoodAndRefreshmentsInfo *> *)foodInfoArr{
    _foodInfoArr=foodInfoArr;
    [self.collectionView reloadData];
}

#pragma mark -collectionView 代理
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    
    return self.foodInfoArr.count;
}


// The cell that is returned must be retrieved from a call to -dequeueReusableCellWithReuseIdentifier:forIndexPath:
- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    ReserveViewCellCollectionViewCell *cell=[collectionView dequeueReusableCellWithReuseIdentifier:@"ReserveViewCellCollectionViewCell" forIndexPath:indexPath];
    
    cell.addValueNameLabel.text=self.foodInfoArr[indexPath.row].addValueServiceName;
    [self sendImageRequest:self.foodInfoArr[indexPath.row].addValueServicePath andCell:cell andDefaultImageName:@"purified_water.png"];
    cell.selectedImage.hidden=YES;
    for (FoodAndRefreshmentsInfo *selectFoodInfo in _selectFoodInfoArr) {
        if ([selectFoodInfo.addValueServiceId isEqualToString:self.foodInfoArr[indexPath.row].addValueServiceId]) {
            cell.selectedImage.hidden=NO;
        }
    }
    return cell;
}

//定义每个UICollectionView 的大小
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath
{
    return CGSizeMake(APPW*2/9, APPW*2/9+20);
}


- (UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout insetForSectionAtIndex:(NSInteger)section {
    return UIEdgeInsetsMake(0, APPW/18, 0, APPW/18);
}
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout minimumInteritemSpacingForSectionAtIndex:(NSInteger)section{

    return APPW/9-1;
}
- (CGFloat)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout minimumLineSpacingForSectionAtIndex:(NSInteger)section
{
    
    return 10.0f;
}


//UICollectionView被选中时调用的方法
-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    [self closeTextKeyboard];
    ReserveViewCellCollectionViewCell * cell = (ReserveViewCellCollectionViewCell *)[collectionView cellForItemAtIndexPath:indexPath];
    
    if (cell.selectedImage.hidden) {
        cell.selectedImage.hidden=NO;
        [_selectFoodInfoArr addObject:_foodInfoArr[indexPath.row]];
    }else{
        cell.selectedImage.hidden=YES;
        [_selectFoodInfoArr removeObject:_foodInfoArr[indexPath.row]];
    }
}

#pragma mark -请求图片
- (void)sendImageRequest:(NSString *)urlString andCell:(ReserveViewCellCollectionViewCell*)cell andDefaultImageName:(NSString *)imageName
{
    [UserImageCacheManager loadHeaderImageWithUrl:urlString placeholder:imageName imageView:cell.AddValueServiceImage];
}


#pragma mark - 开始编辑
- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    self.selectText = textField;
}


#pragma mark - 输入框输入限制条件
- (void)textFieldDidChange:(UITextField *)textField
{
    if (textField == self.detailedAddress)
    {
        if (textField.text.length > 15)
        {
            textField.text = [textField.text substringToIndex:15];
        }
    }
}

#pragma mark - 输入框输入限制条件
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
        NSString *regex = @"";
        
        // 判断的字符串
        NSString * toBeString = [textField.text stringByReplacingCharactersInRange:range withString:string];
        if (self.detailedAddress == textField){
            
            if (toBeString.length<=15) {
                return YES;
            }
            
        }else if(self.iphoneNum == textField){
            // 编写正则表达式：只能是数字或英文，或两者都存在
            regex = @"^[0-9]{0,11}$";
            // 创建谓词对象并设定条件的表达式
            NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", regex];
            // 对字符串进行判断
            if ([predicate evaluateWithObject:toBeString]) {
                return YES;
                
            }
            
        }
        return NO;
        
}

#pragma mark - 点击屏幕退出编辑
-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [self endEditing:YES];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    
    return YES;
}
#pragma mark 弹出选择地址控件
- (IBAction)regionAddressAction:(id)sender {
    [self closeTextKeyboard];
    self.addressBlock();
}

-(void)closeTextKeyboard{
    [_iphoneNum resignFirstResponder];
    [_detailedAddress resignFirstResponder];
}
#pragma mark 弹出选择时间控件
- (IBAction)meetingTimeAction:(id)sender {
    [self closeTextKeyboard];
    self.block();
}




/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
