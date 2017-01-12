//
//  ChineseString.h
//  Created by leo on 15/8/11.
//  Copyright (c) 2015年 fo. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "pinyin.h"
#import "TKAddressBook.h"
@interface PinYinString : NSObject
@property(strong,nonatomic)NSString *string;
@property(strong,nonatomic)NSString *pinYin;
@property(nonatomic,strong) TKAddressBook *book;//图书对象
//-----  返回tableview右方indexArray
+(NSMutableArray*)IndexArray:(NSArray*)stringArr;

//-----  返回联系人
+(NSMutableArray*)LetterSortArray:(NSArray*)stringArr;

///----------------------
//返回一组字母排序数组(中英混排)
+(NSMutableArray*)SortArray:(NSArray*)stringArr;

@end
