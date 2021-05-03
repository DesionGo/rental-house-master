package com.rentalHouseAdmin.rha.common.ext.ueditor.hunter;

import com.rentalHouseAdmin.rha.common.ext.ueditor.define.AppInfo;
import com.rentalHouseAdmin.rha.common.ext.ueditor.PathFormat;
import com.rentalHouseAdmin.rha.common.ext.ueditor.define.BaseState;
import com.rentalHouseAdmin.rha.common.ext.ueditor.define.MultiState;
import com.rentalHouseAdmin.rha.common.ext.ueditor.define.State;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
		this.dir = this.rootPath + (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
		
	}
	
	public com.rentalHouseAdmin.rha.common.ext.ueditor.define.State listFile (int index ) {
		
		File dir = new File( this.dir );
		com.rentalHouseAdmin.rha.common.ext.ueditor.define.State state = null;

		if ( !dir.exists() ) {
			return new com.rentalHouseAdmin.rha.common.ext.ueditor.define.BaseState( false, AppInfo.NOT_EXIST );
		}
		
		if ( !dir.isDirectory() ) {
			return new com.rentalHouseAdmin.rha.common.ext.ueditor.define.BaseState( false, AppInfo.NOT_DIRECTORY );
		}
		
		Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
		
		if ( index < 0 || index > list.size() ) {
			state = new com.rentalHouseAdmin.rha.common.ext.ueditor.define.MultiState( true );
		} else {
			Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
			state = this.getState( fileList );
		}
		
		state.putInfo( "start", index );
		state.putInfo( "total", list.size() );
		
		return state;
		
	}
	
	private State getState (Object[] files ) {
		
		com.rentalHouseAdmin.rha.common.ext.ueditor.define.MultiState state = new MultiState( true );
		com.rentalHouseAdmin.rha.common.ext.ueditor.define.BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			state.addState( fileState );
		}
		
		return state;
		
	}
	
	private String getPath ( File file ) {
		
		String path = file.getAbsolutePath();
		
		return path.replace( this.rootPath, "/" );
		
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}
	
}
