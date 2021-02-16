# Creating simple Image for pricingengine
######################################
# Step 1: Get alpine with jdk8
######################################
FROM openjdk:8-jdk-alpine

#########################################################################
# Step 2: Copy from assets folder to appropriate locations to call run.sh
#########################################################################
COPY assets/ /opt/assets/

COPY build/libs/*.jar pricingengine.jar

##########################################
# Step 3: Expose Ports
##########################################
EXPOSE 9595

##########################################
# Step 4: Start the pricingengine Service
##########################################
CMD sh -C '/opt/assets/run.sh'