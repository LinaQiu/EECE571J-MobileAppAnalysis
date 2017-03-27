### Reference
[How to prevent “Write Failed: broken pipe” on SSH connection?](http://askubuntu.com/questions/127369/how-to-prevent-write-failed-broken-pipe-on-ssh-connection)
[SSH Write failed: Broken pipe. Was working previously](https://forums.aws.amazon.com/thread.jspa?threadID=232873)


### How to Config
* if Linux, go to `/etc/ssh/ssh_config`; if Mac, go to `~/.ssh/config`
* add
```
Host *
ServerAliveInterval 120
```
keep the format same as other params in that file (copy space instead of typing by yourself)
* add on both server side and client side


