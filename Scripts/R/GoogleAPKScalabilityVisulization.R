library(ggplot2)
library(plyr)

# Read info.
GoogleApkInfo <- read.csv("/Users/lina/Documents/EECE571J/Project/GoogleApkFDRunningInfo.csv", header = TRUE)

# Exclude those apps that we did not run analyze on.
FD_AnalyzedGoogleApk=subset(GoogleApkInfo, GoogleApkInfo$analysisResults.FD.!="Did not run")
DS_AnalyzedGoogleApk=subset(GoogleApkInfo, GoogleApkInfo$analysisResults.DS.!="Did not run")

## Draw bar plot for FlowDroid
FD_scalability_bar <- ggplot(FD_AnalyzedGoogleApk, aes(x=app, y=dex2jarSizeInByte))+
	geom_bar(stat = "identity", binwidth=1, aes(fill=analysisResults.FD., order=desc(dex2jarSizeInByte)))+
	theme(axis.text.x=element_blank(),axis.ticks.x=element_blank(),axis.text.y=element_blank())
print(FD_scalability_bar)

## Draw dot plot for FlowDroid
FD_scalability_dot <- ggplot(FD_AnalyzedGoogleApk, aes(x=app, y=dex2jarSizeInByte))+
	geom_point(aes(colour=analysisResults.FD.), size=3)+
	theme(axis.text.x=element_blank(),axis.ticks.x=element_blank(),axis.text.y=element_blank(),axis.ticks.y=element_blank())+
	theme(panel.grid.major = element_blank(), panel.grid.minor = element_blank(),
				panel.background = element_blank(), axis.line = element_line(colour = "black"))+
	theme(axis.text=element_text(size=22),axis.title=element_text(size=24,face="bold"), 
				legend.text=element_text(size=20), legend.title=element_text(size = 20, face = "bold"),
				title=element_text(size = 24, face = "bold"))+
	ggtitle("Scalability running results (FlowDroid)")
print(FD_scalability_dot)

## Draw dot plot for DroidSafe
DS_scalability_dot <- ggplot(DS_AnalyzedGoogleApk, aes(x=app, y=dex2jarSizeInByte))+
	geom_point(aes(colour=analysisResults.DS.), size=3)+
	theme(axis.text.x=element_blank(),axis.ticks.x=element_blank(),axis.text.y=element_blank(),axis.ticks.y=element_blank())+
	theme(panel.grid.major = element_blank(), panel.grid.minor = element_blank(),
				panel.background = element_blank(), axis.line = element_line(colour = "black"))+
	theme(axis.text=element_text(size=22),axis.title=element_text(size=24,face="bold"), 
				legend.text=element_text(size=20), legend.title=element_text(size = 20, face = "bold"),
				title=element_text(size = 24, face = "bold"))+
	ggtitle("Scalability running results (DroidSafe)")
print(DS_scalability_dot)

## Represent app by dot
DrawAppAsDot <- function(dataset) {
	APK_DOTS <- ggplot()+
		geom_point(data=dataset, aes(x=app, y=dex2jarSizeInByte),colour="orange",size=3)+
		theme(axis.text.x=element_blank(),axis.ticks.x=element_blank(),axis.text.y=element_blank(),axis.ticks.y=element_blank())+
		theme(panel.grid.major = element_blank(), panel.grid.minor = element_blank(),
					panel.background = element_blank(), axis.line = element_line(colour = "black"))+
		theme(axis.text=element_text(size=22),axis.title=element_text(size=24,face="bold"), 
					legend.text=element_text(size=20), legend.title=element_text(size = 20, face = "bold"),
					title=element_text(size = 24, face = "bold"))+
		ggtitle("First 60 apps in the sample")
	print(APK_DOTS)
}

## Draw all dots to represent all applications while sorting them by dex2jarsize in asending order.
#DrawAppAsDot(GoogleApkInfo)


## Extract partial apps out from the entire sample, to show the scalability methodology
SUBSET_APP <- subset(GoogleApkInfo, GoogleApkInfo$app<61)
# Draw dots for partial apps without grouping them.
DrawAppAsDot(SUBSET_APP)
## Draw dots to represents those partial apps
SUBAPK_DOTS <- ggplot()+
	geom_point(data=SUBSET_APP, aes(x=app, y=dex2jarSizeInByte, colour=factor(group)), size=3)+
	scale_x_continuous(breaks = c(1, 2, 3, 4, 5, 6)) +
	scale_colour_manual(breaks = c("1", "2", "3", "4", "5", "6"),
											labels = c("Group1", "Group2", "Group3",
																	"Group4", "Group5", "Group6"),
											values = c("#E69F00", "#FF3333", "#009E73", 
																 "#FF66B2", "#0072B2", "#D55E00")) +
	theme(axis.text.x=element_blank(),axis.ticks.x=element_blank(),axis.text.y=element_blank(),axis.ticks.y=element_blank())+
	theme(panel.grid.major = element_blank(), panel.grid.minor = element_blank(),
				panel.background = element_blank(), axis.line = element_line(colour = "black"))+
	theme(axis.text=element_text(size=22),axis.title=element_text(size=24,face="bold"), 
				legend.text=element_text(size=20), legend.title=element_text(size = 20, face = "bold"),
				title=element_text(size = 24, face = "bold"))+
	ggtitle("517 Google Apps ordered by JAR size")
print(SUBAPK_DOTS)

SUBAPK_DOTS_WITH_HIGHLIGHTS <- ggplot()+
	geom_point(data=SUBSET_APP, aes(x=app, y=dex2jarSizeInByte, colour=factor(group), size=ifelse(app%%10==0,2,1)))+
	scale_x_continuous(breaks = c(1, 2, 3, 4, 5, 6)) +
	scale_colour_manual(breaks = c("1", "2", "3", "4", "5", "6"),
											labels = c("Group1", "Group2", "Group3",
																 "Group4", "Group5", "Group6"),
											values = c("#E69F00", "#FF3333", "#009E73", 
																 "#FF66B2", "#0072B2", "#D55E00")) +
	theme(axis.text.x=element_blank(),axis.ticks.x=element_blank(),axis.text.y=element_blank(),axis.ticks.y=element_blank())+
	theme(panel.grid.major = element_blank(), panel.grid.minor = element_blank(),
				panel.background = element_blank(), axis.line = element_line(colour = "black"))+
	theme(axis.text=element_text(size=22),axis.title=element_text(size=24,face="bold"), 
				legend.text=element_text(size=20), legend.title=element_text(size = 20, face = "bold"),
				title=element_text(size = 24, face = "bold"))+guides(size=FALSE)+
	ggtitle("517 Google Apps ordered by JAR size")
print(SUBAPK_DOTS_WITH_HIGHLIGHTS)



