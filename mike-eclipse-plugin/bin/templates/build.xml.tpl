<?xml version="1.0" encoding="UTF-8"?>
<!-- ==============================================================================                                                        

     project : {0}   
     description : Ant build file for {0} Mike example web application
                                                                                  
     =============================================================================== -->
<project name="{0}" default="package">
    <description>
            Build file for the {0} Mike example web application
    </description>
	
	<!-- ================================= 
          target: init              
         ================================= -->
    <target name="init" 
    		description="Initialise the properties, filesets and references required for the build." unless="init.done">
    	
		<echo message="**********************************************" />
		<echo message="* Building $'{'ant.project.name'}'" />
		<echo message="**********************************************" />
    	
    	<property file="$'{'basedir'}'/default.properties"/>
    	
    	<fileset dir="$'{'project.webapp.dir'}'" id="webroot">
    		<include name="**/*.jsp"/>
    		<exclude name="WEB-INF/**"/>
    	</fileset>
    	
    	<fileset dir="$'{'project.webapp.dir'}'/WEB-INF" id="webinf">
    		<include name="**/*.jsp"/>
    		<include name="**/*.xml"/>
    	</fileset>
    	
    	<fileset dir="$'{'project.webapp.dir'}'/WEB-INF/lib" id="webinf.lib">
    		<include name="*.jar"/>
    	</fileset>
    	
    	<fileset dir="$'{'project.lib.dir'}'" id="test.lib">
    		<include name="*.jar"/>
    	</fileset>
    	
    	<path id="compile.classpath">
    		<fileset refid="webinf.lib"/>
    		<pathelement location="$'{'project.build.classes.dir'}'"/>
    	</path>	
    	
    	<path id="testcompile.classpath">
    		<path refid="compile.classpath"/>
    		<fileset refid="test.lib"/>
    		<pathelement location="$'{'project.build.testclasses.dir'}'"/>
    	</path>	
        
    	<property name="init.done" value="true"/>
    	
    </target>

    <!-- ================================= 
          target: clean              
         ================================= -->
    <target name="clean" depends="init" description="Cleans the generated build output directories.">
        
    	<delete dir="$'{'project.build.classes.dir'}'"/>
    	<delete dir="$'{'project.build.testclasses.dir'}'"/>
    	<delete dir="$'{'project.reports.dir'}'"/>
    	<delete dir="$'{'project.build.dir'}'"/>
    	
    </target>

	
	<!-- ================================= 
          target: prepare              
         ================================= -->
    <target name="prepare" depends="init" description="Creates the build output directories.">
        
    	<mkdir dir="$'{'project.build.dir'}'"/>
    	<mkdir dir="$'{'project.build.classes.dir'}'"/>
    	<mkdir dir="$'{'project.build.testclasses.dir'}'"/>
    	<mkdir dir="$'{'project.reports.dir'}'"/>
    	<mkdir dir="$'{'project.reports.junit.dir'}'"/>
    	
    </target>
	
	<!-- ================================= 
          target: process-resources              
         ================================= -->
    <target name="process-resources" depends="prepare"
    	description="Copies file resources into the build output directories">
        <copy toDir="$'{'project.build.classes.dir'}'">
        	<fileset dir="$'{'project.src.dir'}'">
	        	<include name="**/*.properties"/>
	        	<include name="**/*.xml"/>
	        </fileset>	
        </copy>
    </target>
	
	<!-- ================================= 
          target: process-test-resources              
         ================================= -->
    <target name="process-test-resources" depends="process-resources" unless="test.skip"
    	description="Copies file resources into the build test output directories">
        <copy toDir="$'{'project.build.testclasses.dir'}'">
        	<fileset dir="$'{'project.testsrc.dir'}'">
	        	<include name="**/*.properties"/>
	        	<include name="**/*.xml"/>
	        </fileset>	
        </copy>
    </target>

	
	<!-- ================================= 
          target: compile              
         ================================= -->
    <target name="compile" depends="process-test-resources" 
    		description="Compiles the code.">
    	
        <javac srcdir="$'{'project.src.dir'}'" debug="$'{'javac.debug'}'" 
    		destdir="$'{'project.build.classes.dir'}'" source="$'{'javac.source'}'">
    		<classpath refid="compile.classpath"/>
    	</javac>
    	
    </target>
	
	
	<!-- ================================= 
          target: test-compile              
         ================================= -->
    <target name="test-compile" depends="compile" 
    	description="Compiles the test code." unless="test.skip">
    	
        <javac srcdir="$'{'project.testsrc.dir'}'" debug="$'{'javac.debug'}'" 
    		destdir="$'{'project.build.testclasses.dir'}'" source="$'{'javac.source'}'">
    		<classpath refid="testcompile.classpath"/>
    	</javac>
    	
    </target>
	
	<!-- ================================= 
          target: test           
         ================================= -->
    <target name="test" depends="test-compile" 
    	description="Runs the Junit tests and generates XML reports." unless="test.skip">
    	
    	<junit haltonfailure="$'{'junit.haltonfailure'}'" printsummary="true">
			<formatter type="xml" />
			<classpath refid="testcompile.classpath"/>
			<batchtest fork="yes" todir="$'{'project.reports.junit.dir'}'">
				<fileset dir="$'{'project.testsrc.dir'}'" 
					includes="$'{'junit.batch.includes'}'" 
					excludes="$'{'junit.batch.excludes'}'"/>
			</batchtest>
		</junit>
    	
    </target>
	
	<!-- ================================= 
          target: package              
         ================================= -->
    <target name="package" depends="test" description="Packages the web application into a WAR.">
        
    	<war file="$'{'project.archive'}'">
        	<classes dir="$'{'project.build.classes.dir'}'"/>
        	<lib refid="webinf.lib"/>
        	<webinf refid="webinf"/>
        	<fileset refid="webroot"/>
       </war> 
    	
    </target>


	
</project>
