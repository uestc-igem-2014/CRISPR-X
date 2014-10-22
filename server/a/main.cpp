#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "mysql.h"
#include "cJSON/cJSON.h"

#define LEN 20
#define GENE_LEN 80000000
#define PAM_LEN 20

int check_pam(const char *str,const char *pam){
    for(;*pam;pam++,str++){
        if(*pam=='R' && (*str=='A' || *str=='G')) continue;
        if(*pam=='M' && (*str=='A' || *str=='C')) continue;
        if(*pam=='W' && (*str=='A' || *str=='T')) continue;
        if(*pam=='S' && (*str=='C' || *str=='G')) continue;
        if(*pam=='K' && (*str=='G' || *str=='T')) continue;
        if(*pam=='Y' && (*str=='C' || *str=='T')) continue;
        if(*pam=='H' && (*str=='A' || *str=='C' || *str=='T')) continue;
        if(*pam=='V' && (*str=='A' || *str=='C' || *str=='G')) continue;
        if(*pam=='B' && (*str=='C' || *str=='G' || *str=='T')) continue;
        if(*pam=='D' && (*str=='A' || *str=='G' || *str=='T')) continue;
        if(*pam=='N' || *pam=='n') continue;
        if(*pam==*str) continue;
        return 0;
    }
    return 1;
}

char dna_rev_char(char ch){
    if(ch=='A' || ch=='a') return 'T';
    if(ch=='T' || ch=='t') return 'A';
    if(ch=='C' || ch=='c') return 'G';
    if(ch=='G' || ch=='g') return 'C';
    return 'N';
}

char *dna_rev(char *sr,const char *s,int len){
    int i;
    for(i=0;i<len;i++){
        sr[i]=dna_rev_char(s[len-i-1]);
    }
    sr[i]=0;
    return sr;
}

int readLine(FILE *file){
    char ch;
    while(fscanf(file,"%c",&ch)==1) if(ch=='\n') return 1;
    return 0;
}

char *_tos(char *str){
    for(char *p=str;*p;p++){
        if(*p=='_') *p++=' ';
    }
    return str;
}

char str[GENE_LEN];
char buffer[9182];

MYSQL *my_conn;
MYSQL_RES *result;
MYSQL_ROW sql_row;

void get_pam_info(char pam_id[],const char pam[]){
    sprintf(buffer,"SELECT pam_id FROM Table_PAM WHERE pam_PAM='%s'; ",pam);
    mysql_query(my_conn,buffer);
    result=mysql_store_result(my_conn);
    sql_row=mysql_fetch_row(result);
    strcpy(pam_id,sql_row[0]);
}

int sum=0;
char sql[500000]="";
void add(char *str){
    if(sum==0){
        strcat(sql,"INSERT INTO Table_sgRNA (Chr_No, sgrna_start, sgrna_end, sgrna_strand, sgrna_seq, sgrna_PAM, pam_id) VALUES \n");
    }else{
        strcat(sql,",\n");
    }
    strcat(sql,str);
    sum=(sum+1)%500;
    if(sum==0){
        strcat(sql,"; \n");
        int res=mysql_query(my_conn,sql);
        if(res){
            printf("%s\n",mysql_error(my_conn));
        }
        sql[0]=0;
    }
}
void finish(){
    if(sum!=0){
        strcat(sql,"; \n");
        int res=mysql_query(my_conn,sql);
        if(res){
            printf("%s\n",mysql_error(my_conn));
            printf("%s\n",sql);
        }
        sql[0]=0;
    }
}

int getFileID(const char *user_ID,const char *fileName){
    char sql[1024];
    sprintf(sql,"SELECT upload_ID FROM user_upload WHERE user_id=%s AND upload_filename='%s'; ",user_ID,fileName);
    int res=mysql_query(my_conn,sql);
    if(res){
        return -1;
    }
    result=mysql_store_result(my_conn);
    if(result){
        sql_row=mysql_fetch_row(result);
        return atoi(sql_row[0]);
    }else{
        return -1;
    }
    return -1;
}

//char def1[]="{\"specie\":\"kkkk\",\"files\":[{\"fileName\":\"NC_01297\"}],\"PAM\":\"NGG\"}";
//char def2[]="8"
int main(int args, char *argv[]){
    if(args!=3){
        printf("N3");
        return 1;
    }

    int count=0;

    my_bool mb=false;
    my_conn=mysql_init(NULL);
    mysql_options(my_conn,MYSQL_SECURE_AUTH,&mb);
#ifdef  _WIN32
    if(mysql_real_connect(my_conn,"127.0.0.1","root","zy19930108","CasDB",3306,NULL,0)){
#endif
#ifdef  __linux
    if(mysql_real_connect(my_conn,"127.0.0.1","igem","uestc2014!","CasDB",3306,NULL,0)){
#endif
        cJSON *root=cJSON_Parse(argv[1]);
        if(root==NULL){
            printf("N4");
            return 1;
        }

        char Sno[15],SName[100+1];
        strcpy(SName,cJSON_GetObjectItem(root,"specie")->valuestring);
		if(strcmp(argv[2],"0")==0) sprintf(buffer,"INSERT INTO Table_Specie(SName) VALUES('%s-%s'); ",argv[2],SName);
        else sprintf(buffer,"INSERT INTO Table_Specie(SName,user_id) VALUES('%s-%s',%s); ",argv[2],SName,argv[2]);
        printf("%s-%s",argv[2],SName);
        mysql_query(my_conn,buffer);
        result=mysql_store_result(my_conn);
        mysql_free_result(result);
        mysql_query(my_conn,"SELECT LAST_INSERT_ID(); ");
        result=mysql_store_result(my_conn);
        sql_row=mysql_fetch_row(result);
        strcpy(Sno,sql_row[0]);
        mysql_free_result(result);
printf("\n%s\n",Sno);
        char pam[20+1],pam_id[15];
        strcpy(pam,cJSON_GetObjectItem(root,"PAM")->valuestring);
        get_pam_info(pam_id,pam);
        int req_pam_len=strlen(pam);

        cJSON *files=cJSON_GetObjectItem(root,"files");
        int len=cJSON_GetArraySize(files);

        for(int k=0;k<len;k++){
            char Chr_No[15];
            cJSON *node=cJSON_GetArrayItem(files,k);
            sprintf(buffer,"INSERT INTO Table_chromosome(SNo,Chr_Name) VALUES('%s','%s'); ",Sno,cJSON_GetObjectItem(node,"fileName")->valuestring);
            mysql_query(my_conn,buffer);
            mysql_query(my_conn,"SELECT LAST_INSERT_ID(); ");
            result=mysql_store_result(my_conn);
            sql_row=mysql_fetch_row(result);
            strcpy(Chr_No,sql_row[0]);
            mysql_free_result(result);
                
                if(strcmp(argv[2],"0")==0 && cJSON_GetObjectItem(node,"filePath")) sprintf(buffer,cJSON_GetObjectItem(node,"filePath")-valuestring);
                else sprintf(buffer,"../uploaddata/%d",getFileID(argv[2],cJSON_GetObjectItem(node,"fileName")->valuestring));
printf("%s\n",buffer);
                FILE *ff=fopen(buffer,"r");
                readLine(ff);
                int i=1,j;
                int len;
                char ch;
                while(fscanf(ff,"%c",&ch)==1){
                    if(ch==10) continue;
                    str[i++]=ch;
                }
                str[i]=0;
                len=i;
                fclose(ff);

                char thispam[20],nt[LEN+1];
                for(i=LEN;i<len-req_pam_len;i++){       // All possible gRNAs, +direction
                    if(check_pam(str+i,pam)){
                        for(j=0;j<LEN;j++) nt[j]=(str+i-LEN)[j];
                        nt[j]=0;
                        for(j=0;j<req_pam_len;j++) thispam[j]=(str+i)[j];
                        thispam[j]=0;   //(Chr_No, sgrna_start, sgrna_end, sgrna_strand, sgrna_seq, sgrna_PAM, pam_id)
                        sprintf(buffer,"(%s, %d, %d, '%c', '%s', '%s', %s)",Chr_No,i-LEN,i-1+req_pam_len,'+',nt,thispam,pam_id);
                        add(buffer);
                        count++;
                    }
                }
                char req_pam_rev[PAM_LEN];
                dna_rev(req_pam_rev,pam,req_pam_len);
                for(i=0;i<len-LEN-req_pam_len;i++){     // All possible gRNAs, -direction
                    if(check_pam(str+i,req_pam_rev)){
                        for(j=0;j<LEN;j++) nt[j]=dna_rev_char((str+i+req_pam_len)[LEN-j-1]);
                        nt[j]=0;
                        for(j=0;j<req_pam_len;j++) thispam[j]=dna_rev_char((str+i)[req_pam_len-j-1]);
                        thispam[j]=0;
                        sprintf(buffer,"(%s, %d, %d, '%c', '%s', '%s', %s)",Chr_No,i,i+req_pam_len+LEN-1,'-',nt,thispam,pam_id);
                        add(buffer);
                        count++;
                    }
                }
        }
        finish();
    }
    mysql_close(my_conn);

    return 0;
}
