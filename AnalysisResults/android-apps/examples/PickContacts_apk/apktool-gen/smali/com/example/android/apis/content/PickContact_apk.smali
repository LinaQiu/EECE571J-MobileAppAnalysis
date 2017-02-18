.class public Lcom/example/android/apis/content/PickContact_apk;
.super Landroid/app/Activity;
.source "PickContact_apk.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;
    }
.end annotation


# instance fields
.field mPendingResult:Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;

.field mToast:Landroid/widget/Toast;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 51
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    .line 55
    return-void
.end method


# virtual methods
.method protected onActivityResult(IILandroid/content/Intent;)V
    .locals 14
    .param p1, "requestCode"    # I
    .param p2, "resultCode"    # I
    .param p3, "data"    # Landroid/content/Intent;

    .prologue
    .line 102
    if-eqz p3, :cond_2

    .line 103
    const-string v1, "Device ID"

    move-object/from16 v0, p3

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    .line 107
    .local v9, "deviceID":Ljava/lang/String;
    :try_start_0
    new-instance v11, Lorg/apache/http/client/methods/HttpGet;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "http:www.google.com?query="

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v11, v1}, Lorg/apache/http/client/methods/HttpGet;-><init>(Ljava/lang/String;)V

    .line 108
    .local v11, "hget":Lorg/apache/http/client/methods/HttpGet;
    new-instance v8, Lorg/apache/http/impl/client/DefaultHttpClient;

    invoke-direct {v8}, Lorg/apache/http/impl/client/DefaultHttpClient;-><init>()V

    .line 109
    .local v8, "client":Lorg/apache/http/client/HttpClient;
    invoke-interface {v8, v11}, Lorg/apache/http/client/HttpClient;->execute(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 114
    .end local v8    # "client":Lorg/apache/http/client/HttpClient;
    .end local v11    # "hget":Lorg/apache/http/client/methods/HttpGet;
    :goto_0
    invoke-virtual/range {p3 .. p3}, Landroid/content/Intent;->getData()Landroid/net/Uri;

    move-result-object v2

    .line 116
    .local v2, "uri":Landroid/net/Uri;
    if-eqz v2, :cond_2

    .line 117
    const/4 v7, 0x0

    .line 119
    .local v7, "c":Landroid/database/Cursor;
    :try_start_1
    invoke-virtual {p0}, Lcom/example/android/apis/content/PickContact_apk;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/String;

    const/4 v4, 0x0

    const-string v5, "_id"

    aput-object v5, v3, v4

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    invoke-virtual/range {v1 .. v6}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v7

    .line 121
    if-eqz v7, :cond_1

    invoke-interface {v7}, Landroid/database/Cursor;->moveToFirst()Z

    move-result v1

    if-eqz v1, :cond_1

    .line 122
    const/4 v1, 0x0

    invoke-interface {v7, v1}, Landroid/database/Cursor;->getInt(I)I

    move-result v12

    .line 123
    .local v12, "id":I
    iget-object v1, p0, Lcom/example/android/apis/content/PickContact_apk;->mToast:Landroid/widget/Toast;

    if-eqz v1, :cond_0

    .line 124
    iget-object v1, p0, Lcom/example/android/apis/content/PickContact_apk;->mToast:Landroid/widget/Toast;

    invoke-virtual {v1}, Landroid/widget/Toast;->cancel()V

    .line 126
    :cond_0
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/example/android/apis/content/PickContact_apk;->mPendingResult:Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;

    iget-object v3, v3, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;->mMsg:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, ":\n"

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, "\nid: "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v12}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    .line 128
    .local v13, "txt":Ljava/lang/String;
    const/4 v1, 0x1

    invoke-static {p0, v13, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    iput-object v1, p0, Lcom/example/android/apis/content/PickContact_apk;->mToast:Landroid/widget/Toast;

    .line 129
    iget-object v1, p0, Lcom/example/android/apis/content/PickContact_apk;->mToast:Landroid/widget/Toast;

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 132
    .end local v12    # "id":I
    .end local v13    # "txt":Ljava/lang/String;
    :cond_1
    if-eqz v7, :cond_2

    .line 133
    invoke-interface {v7}, Landroid/database/Cursor;->close()V

    .line 138
    .end local v2    # "uri":Landroid/net/Uri;
    .end local v7    # "c":Landroid/database/Cursor;
    .end local v9    # "deviceID":Ljava/lang/String;
    :cond_2
    return-void

    .line 110
    .restart local v9    # "deviceID":Ljava/lang/String;
    :catch_0
    move-exception v10

    .line 111
    .local v10, "e":Ljava/lang/Exception;
    invoke-virtual {v10}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0

    .line 132
    .end local v10    # "e":Ljava/lang/Exception;
    .restart local v2    # "uri":Landroid/net/Uri;
    .restart local v7    # "c":Landroid/database/Cursor;
    :catchall_0
    move-exception v1

    if-eqz v7, :cond_3

    .line 133
    invoke-interface {v7}, Landroid/database/Cursor;->close()V

    :cond_3
    throw v1
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 4
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 79
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 81
    const/high16 v0, 0x7f020000

    invoke-virtual {p0, v0}, Lcom/example/android/apis/content/PickContact_apk;->setContentView(I)V

    .line 84
    const/high16 v0, 0x7f040000

    invoke-virtual {p0, v0}, Lcom/example/android/apis/content/PickContact_apk;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    new-instance v1, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;

    const-string v2, "Selected contact"

    const-string v3, "vnd.android.cursor.item/contact"

    invoke-direct {v1, p0, v2, v3}, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;-><init>(Lcom/example/android/apis/content/PickContact_apk;Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 87
    const v0, 0x7f040001

    invoke-virtual {p0, v0}, Lcom/example/android/apis/content/PickContact_apk;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    new-instance v1, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;

    const-string v2, "Selected person"

    const-string v3, "vnd.android.cursor.item/person"

    invoke-direct {v1, p0, v2, v3}, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;-><init>(Lcom/example/android/apis/content/PickContact_apk;Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 90
    const v0, 0x7f040002

    invoke-virtual {p0, v0}, Lcom/example/android/apis/content/PickContact_apk;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    new-instance v1, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;

    const-string v2, "Selected phone"

    const-string v3, "vnd.android.cursor.item/phone_v2"

    invoke-direct {v1, p0, v2, v3}, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;-><init>(Lcom/example/android/apis/content/PickContact_apk;Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 93
    const v0, 0x7f040003

    invoke-virtual {p0, v0}, Lcom/example/android/apis/content/PickContact_apk;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/Button;

    new-instance v1, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;

    const-string v2, "Selected address"

    const-string v3, "vnd.android.cursor.item/postal-address_v2"

    invoke-direct {v1, p0, v2, v3}, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;-><init>(Lcom/example/android/apis/content/PickContact_apk;Ljava/lang/String;Ljava/lang/String;)V

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 97
    return-void
.end method
