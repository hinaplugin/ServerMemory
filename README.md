# ServerMemory
サーバーのメモリ使用量を見ることができます

サーバーのメモリ使用量をコマンドで確認することができ，
権限を持っている方全員に15分毎にチャットで使用量をお知らせします．

# Commands
```
＜チャット通知システムの開始＞
/memory start

＜チャット通知システムの停止＞
/memory stop

＜メモリ使用量の確認（個人）＞
/memory get

＜メモリ使用量の確認（権限保持者全員）＞
/memory announce
```

# Permissions
```
memory.admin
  - server.system.command
  - server.system.info

server.system.command
（コマンドを実行するための権限）

server.system.info
（チャット通知システムからの通知を受け取るための権限）
