我们将上传文件分为三个部分：
1. 检查是否可以上传文件（upload/check.php）
2. 上传文件（upload/）
3. 将上传的文件导入数据库（upload/import.php），同时获得一个 ID
一切都完成后我们就可以在 getMain.php 中将获得的 ID 作为 specie 运行主程序了。
只不过这个物种将没有基因、区域等信息。

IMPORTANT! 
Due to the out-of-date file in this server, result are not 100% correct. 
And for the safety of out database, import.php will actually not import data into database. 
Please check import sql file tmp/out.sql

