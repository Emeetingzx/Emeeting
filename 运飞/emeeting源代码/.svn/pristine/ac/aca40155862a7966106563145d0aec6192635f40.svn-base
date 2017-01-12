//
//  EMMURLConnectionOperation+SOAP.m
//  RedPacket
//
//  Created by itp on 15/11/4.
//  Copyright © 2015年 itp. All rights reserved.
//

#import "EMMURLConnectionOperation+SOAP.h"
#import "GDataXMLElement+SOAP.h"

@implementation EMMURLConnectionOperation (SOAP)

- (id)soapJSON
{
    NSString *responseString = [[NSString alloc] initWithData:self.responseData encoding:self.responseStringEncoding];
    GDataXMLDocument *document = [[GDataXMLDocument alloc] initWithXMLString:responseString options:0 error:NULL];
    GDataXMLElement *rootElement = [document rootElement];
    GDataXMLElement *resultElement = [rootElement resultElementWithresponseName:@"ns:WSServiceResponse" resultName:@"ns:return"];
    NSLog(@"soap return %@",[resultElement stringValue]);
    NSString *string = [resultElement stringValue];
    string = [self decryptContent:string];
    if (string.length == 0)
    {
        return nil;
    }
    NSError *error = nil;
    NSData *data = [string dataUsingEncoding:NSUTF8StringEncoding];
    id returnValue = [NSJSONSerialization JSONObjectWithData:data options:0 error:&error];
    if(error) NSLog(@"JSON Parsing Error: %@", error);
    return returnValue;
}

@end
