package spring.cloud.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.cloud.biz.dataaccess.dataobject.MomentDo;
import spring.cloud.biz.dataaccess.mapper.MomentDoMapper;
import spring.cloud.biz.service.MomentService;
import spring.cloud.client.model.MomentModel;
import spring.cloud.client.uitils.CopyProperityUtils;
import spring.cloud.demo.model.ListResultModel;
import spring.cloud.demo.model.ResultModel;
import spring.cloud.demo.model.TraceIdHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Harry on 15/12/2017.
 */

@Service("momentService")
public class MomentServiceImpl implements MomentService {

    private static final Logger LOGGER = LoggerFactory.getLogger( MomentServiceImpl.class );

    @Autowired private MomentDoMapper momentDoMapper;

    /*  by default, @Transactional uses the primary txManager */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, Throwable.class})
    public ListResultModel<MomentModel> listFirstPageMoment(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "id desc");
        List<MomentDo> momentDoList = this.momentDoMapper.listMoment();

        PageInfo<MomentDo> pageInfo = new PageInfo<>( momentDoList );
        ListResultModel<MomentModel> resultModel = ListResultModel.createSuccess();
        resultModel.setPageNo( pageInfo.getPageNum() );
        resultModel.setTotalPages( pageInfo.getPages() );
        resultModel.setTotalCount( pageInfo.getTotal() );

        if ( null == momentDoList || momentDoList.isEmpty() ) {
            return resultModel;
        }

        List<MomentModel> modelList = new ArrayList<>();
        momentDoList.forEach( momentDo -> {
            MomentModel momentModel = new MomentModel();
            CopyProperityUtils.copyAllProperies( momentDo, momentModel );
            modelList.add( momentModel );
        } );

        resultModel.setData( modelList );

        return resultModel;
    }

    @Override
    public ResultModel<MomentModel> addMoment(String userId, String content) {
        if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty( content ) ) {
            LOGGER.error("traceId:{} addMoment, invalidParam, userId:{}, content:{}",
                    TraceIdHelper.getTraceId(), userId, content);
            return ResultModel.createFail("invalidParam");
        }
        MomentDo momentDo = new MomentDo();
        momentDo.setUserId( userId );
        momentDo.setContent(content);
        momentDo.setGmtCreated(new Date());
        momentDo.setGmtModified( new Date() );
        momentDo.setIsDeleted(false);

        this.momentDoMapper.insert( momentDo );
        MomentModel momentModel = new MomentModel();
        CopyProperityUtils.copyAllProperies( momentDo, momentModel);

        return ResultModel.createSuccess( momentModel );
    }
}
