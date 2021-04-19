package cn.stylefeng.guns.modular.system.service.impl;

import cn.stylefeng.guns.core.util.Contrast;
import cn.stylefeng.guns.core.util.MyException;
import cn.stylefeng.guns.modular.system.dao.ToneinfoMapper;
import cn.stylefeng.guns.modular.system.model.Toneinfo;
import cn.stylefeng.guns.modular.system.service.IToneinfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 歌曲信息表 服务实现类
 * </p>
 *
 * @author xiefengyu
 * @since 2018-11-14
 */
@Service
public class ToneinfoServiceImpl extends ServiceImpl<ToneinfoMapper, Toneinfo>implements IToneinfoService
{

	@Override
	public List<Map<String, Object>> list( String condition )
	{
		return this.baseMapper.list( condition );
	}

	@Autowired
	private ToneinfoMapper toneinfoMapper;

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@Override
	public boolean batchImport( String fileName, MultipartFile file ) throws Exception
	{

		boolean notNull = false;
		List<Toneinfo> toneinfoList = new ArrayList<Toneinfo>();
		if ( !fileName.matches( "^.+\\.(?i)(xls)$" ) && !fileName.matches( "^.+\\.(?i)(xlsx)$" ) )
		{
			throw new MyException( "上传文件格式不正确" );
		}
		boolean isExcel2003 = true;
		if ( fileName.matches( "^.+\\.(?i)(xlsx)$" ) )
		{
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if ( isExcel2003 )
		{
			wb = new HSSFWorkbook( is );
		} else
		{
			wb = new XSSFWorkbook( is );
		}
		Sheet sheet = wb.getSheetAt( 0 );
		if ( sheet != null )
		{
			notNull = true;
		}
		Toneinfo toneinfo;
		for ( int r = 1; r <= sheet.getLastRowNum(); r++ )
		{
			Row row = sheet.getRow( r );
			if ( row == null )
			{
				continue;
			}

			toneinfo = new Toneinfo();
			toneinfo.setSongId( Contrast.getData( row, 0 ) );
			toneinfo.setSongName( Contrast.getData( row, 1 ) );
			toneinfo.setSingerName( Contrast.getData( row, 2 ) );
			toneinfo.setCreatetime( new Date() );
			toneinfoList.add( toneinfo );

		}
		for ( Toneinfo toneinfoResord : toneinfoList )
		{
			//省去代码千万行
			EntityWrapper<Toneinfo> toneinfoEntityWrapper=new EntityWrapper<Toneinfo>();
			String songId = toneinfoResord.getSongId();
			toneinfoEntityWrapper.eq("songId",songId);
			Integer count = toneinfoMapper.selectCount(toneinfoEntityWrapper);
//			int cnt = toneinfoMapper.selectBySongId( songId );
			if (count == 0)
			{
				toneinfoMapper.insert(toneinfoResord);
//				toneinfoMapper.addToneinfo( toneinfoResord );
				System.out.println( " 插入 " + toneinfoResord );
			} else
			{
				toneinfoMapper.update(toneinfoResord,toneinfoEntityWrapper);
//				toneinfoMapper.updateToneinfoBySongId( toneinfoResord );

				System.out.println( " 更新 " + toneinfoResord );
			}
		}
		notNull = true;
		return notNull;
	}

}
