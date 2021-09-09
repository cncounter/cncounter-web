# mysqldump --help
# cat dbnames_to_dump.txt  | xargs -I {} echo back_{}_to_{}

curtime=`date +%Y%m%d%H%M%S`
curdate=`date +%Y%m%d`
backpath=back_$curdate
basepath=/usr/local/daily_backup_mysql/

mkdir -p $basepath/$backpath
cat $basepath/dbnames_to_dump.txt  | xargs -I {} sh -c "mysqldump -u root -proot  {} > $basepath/$backpath/{}.dump.sql"


