package edu.dyw.resume.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.dyw.resume.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}
