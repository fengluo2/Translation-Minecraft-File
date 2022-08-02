# Translation-Minecraft-Mod-Quests

机翻MC整合包的任务及自定义物品(.lang和.json文件)  
机翻完可以自己用，也可以在此之上进一步汉化

## 命令格式

java -jar QuestsTranslation.jar file:file [id:id key:key speed:speed]

## 使用示例

java -jar QuestsTranslation.jar file:ExpertQuests.json  
java -jar QuestsTranslation.jar file:C:\\\Users\\\Maple\\\Desktop\\\en_us.lang id:1234567890 key;1234567890 speed:1000  
file参数是必须的  
id,key,speed参数不是必须的

## 注意事项

1.Java版本用的是11，1.8用不了！  
2.用的是百度翻译的api，在不改变参数的情况下，默认用的是我的百度翻译的账户（不保证能用很久），每秒最多翻译一次（硬性限制，因为只用了百度翻译的基础服务）建议自己注册一个百度翻译的api  
3.第一个版本应该还有一些Bug...但还没测出来，欢迎反馈

## 接下来可能的计划

1.加入本地词典  
2.加入其他API

## 一些链接

[BilBil](https://www.bilibili.com/read/cv16729313)
