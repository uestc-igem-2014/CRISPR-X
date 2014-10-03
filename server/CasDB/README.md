# CasDB

CasDB contains our pre-calculated results, so it largely enhances our software speed.

## How we get this version of CasDB

Default MySQL username is `igem`
We export our origin CasDB using these fellow commands in shell:

```shell
mysqldump -u igem -p CasDB Table_PAM Table_Specie Table_chromosome Table_gene region_type_define > CasDB_part1.sql
mysqldump -u igem -p CasDB Table_region > CasDB_part2.sql
mysqldump -u igem -p CasDB Table_sgRNA --where="Chr_No=10" > CasDB_part3.sql
mysqldump -u igem -p CasDB Table_result > CasDB_part4.sql
tar -zcvf CasDB.tar.gz CasDB_part*
```

## Attention

As you can see, This version just contains part of `Table_sgRNA`. __`CasDB.tar.gz` is used for our automated test on Travis CI__.