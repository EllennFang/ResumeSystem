package edu.dyw.resume.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.dyw.resume.entity.Template;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TemplateMapper extends BaseMapper<Template> {
}
