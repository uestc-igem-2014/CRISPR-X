/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/7/25 15:52:28                           */
/*==============================================================*/


drop table if exists Table_Specie;

drop table if exists Table_chromosome;

drop table if exists Table_gene;

drop table if exists Table_region;

drop table if exists Table_sgRNA;

drop table if exists region_type_define;

/*==============================================================*/
/* Table: Table_Specie                                          */
/*==============================================================*/
create table Table_Specie
(
   Sno                  int not null auto_increment,
   SName                varchar(100) not null,
   primary key (Sno)
);

/*==============================================================*/
/* Table: Table_chromosome                                      */
/*==============================================================*/
create table Table_chromosome
(
   Chr_No               int not null auto_increment,
   Sno                  int not null,
   Chr_Name             varchar(100) not null,
   Chr_Address          varchar(256),
   primary key (Chr_No)
);

/*==============================================================*/
/* Table: Table_gene                                            */
/*==============================================================*/
create table Table_gene
(
   gene_ID              int not null auto_increment,
   Chr_No               int not null,
   gene_Name            varchar(30) not null,
   gene_Start           int not null,
   gene_end             int not null,
   gene_Strand          char(1) not null,
   gene_UTRstart        int,
   gene_UTRend          int,
   primary key (gene_ID)
);

/*==============================================================*/
/* Table: Table_region                                          */
/*==============================================================*/
create table Table_region
(
   region_ID            int not null auto_increment,
   rtd_id               int not null,
   gene_ID              int not null,
   region_start         int not null,
   region_end           int not null,
   region_strand        char(1) not null,
   region_length        int,
   region_PID           int,
   primary key (region_ID)
);

/*==============================================================*/
/* Table: Table_sgRNA                                           */
/*==============================================================*/
create table Table_sgRNA
(
   sgrna_ID             int not null auto_increment,
   region_ID            int,
   Chr_No               int not null,
   sgrna_start          int not null,
   sgrna_end            int not null,
   sgrna_strand         char(1) not null,
   sgrna_seq            char(20) not null,
   sgrna_PAM            char(20) not null,
   sgrna_cuttingsite    int not null,
   sgrna_Sspe           numeric(3,2) not null,
   sgrna_Seff           numeric(3,2) not null,
   sgrna_Sguide         numeric(3,2) not null,
   primary key (sgrna_ID)
);

/*==============================================================*/
/* Table: region_type_define                                    */
/*==============================================================*/
create table region_type_define
(
   rtd_id               int not null,
   rtd_description      varchar(200) not null,
   primary key (rtd_id)
);

alter table Table_chromosome add constraint FK_Relationship_1 foreign key (Sno)
      references Table_Specie (Sno) on delete restrict on update restrict;

alter table Table_gene add constraint FK_Relationship_2 foreign key (Chr_No)
      references Table_chromosome (Chr_No) on delete restrict on update restrict;

alter table Table_region add constraint FK_Relationship_4 foreign key (gene_ID)
      references Table_gene (gene_ID) on delete restrict on update restrict;

alter table Table_region add constraint FK_Relationship_8 foreign key (rtd_id)
      references region_type_define (rtd_id) on delete restrict on update restrict;

alter table Table_sgRNA add constraint FK_Relationship_6 foreign key (Chr_No)
      references Table_chromosome (Chr_No) on delete restrict on update restrict;

alter table Table_sgRNA add constraint FK_Relationship_7 foreign key (region_ID)
      references Table_region (region_ID) on delete restrict on update restrict;

