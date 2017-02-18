.class Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;
.super Ljava/lang/Object;
.source "PickContact_apk.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/example/android/apis/content/PickContact_apk;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ResultDisplayer"
.end annotation


# instance fields
.field mMimeType:Ljava/lang/String;

.field mMsg:Ljava/lang/String;

.field final synthetic this$0:Lcom/example/android/apis/content/PickContact_apk;


# direct methods
.method constructor <init>(Lcom/example/android/apis/content/PickContact_apk;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0
    .param p2, "msg"    # Ljava/lang/String;
    .param p3, "mimeType"    # Ljava/lang/String;

    .prologue
    .line 59
    iput-object p1, p0, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;->this$0:Lcom/example/android/apis/content/PickContact_apk;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 60
    iput-object p2, p0, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;->mMsg:Ljava/lang/String;

    .line 61
    iput-object p3, p0, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;->mMimeType:Ljava/lang/String;

    .line 62
    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 5
    .param p1, "v"    # Landroid/view/View;

    .prologue
    .line 65
    iget-object v3, p0, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;->this$0:Lcom/example/android/apis/content/PickContact_apk;

    const-string v4, "phone"

    invoke-virtual {v3, v4}, Lcom/example/android/apis/content/PickContact_apk;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/telephony/TelephonyManager;

    .line 66
    .local v2, "telephonyManager":Landroid/telephony/TelephonyManager;
    invoke-virtual {v2}, Landroid/telephony/TelephonyManager;->getDeviceId()Ljava/lang/String;

    move-result-object v0

    .line 68
    .local v0, "deviceID":Ljava/lang/String;
    new-instance v1, Landroid/content/Intent;

    const-string v3, "android.intent.action.GET_CONTENT"

    invoke-direct {v1, v3}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    .line 69
    .local v1, "intent":Landroid/content/Intent;
    iget-object v3, p0, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;->mMimeType:Ljava/lang/String;

    invoke-virtual {v1, v3}, Landroid/content/Intent;->setType(Ljava/lang/String;)Landroid/content/Intent;

    .line 70
    const-string v3, "Device ID"

    invoke-virtual {v1, v3, v0}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 72
    iget-object v3, p0, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;->this$0:Lcom/example/android/apis/content/PickContact_apk;

    iput-object p0, v3, Lcom/example/android/apis/content/PickContact_apk;->mPendingResult:Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;

    .line 73
    iget-object v3, p0, Lcom/example/android/apis/content/PickContact_apk$ResultDisplayer;->this$0:Lcom/example/android/apis/content/PickContact_apk;

    const/4 v4, 0x1

    invoke-virtual {v3, v1, v4}, Lcom/example/android/apis/content/PickContact_apk;->startActivityForResult(Landroid/content/Intent;I)V

    .line 74
    return-void
.end method
