#! /bin/zsh
source ~/.zshrc
# 记录脚本开始执行时间
echo '========= githubHosts任务开始执行时间 ======' `date` >> ./runLog
# 执行自定义的脚本，我这里是一个java代码实现
cd ~/work/Code/VCS/github-hosts/
java -jar target/github-hosts-1.0-SNAPSHOT.jar

# 记录任务完成的时间
echo '完成时间：' `date` >> ./runLog

git status
git add .
git commit -m "updated latest hosts"
git push
echo '!!! push success ！！！时间：' `date` >> ./runLog

