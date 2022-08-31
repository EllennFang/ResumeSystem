package edu.dyw.resume.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.dyw.resume.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> getAll();
}
