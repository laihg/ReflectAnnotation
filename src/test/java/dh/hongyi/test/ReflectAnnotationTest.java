package dh.hongyi.test;

import dh.hongyi.annotation.Column;
import dh.hongyi.annotation.FieldCheck;
import dh.hongyi.annotation.FieldMapping;
import dh.hongyi.exception.GenericBusinessException;
import dh.hongyi.exception.MappingException;
import dh.hongyi.utils.BeanUtils;

/**
 * 反射+注解测试类
 */
public class ReflectAnnotationTest {

    public static void main(String[] args) {
        try {
            Group group = new Group("", "竑翼"); // 校验字段是否为空
           /* List<ErrorEntity> errorEntities = BeanUtils.checkFieldIsEmpty(group);
            if(!errorEntities.isEmpty()) {
                System.out.println(errorEntities);
                return;
            }*/
            group = new Group("10001", "竑翼");
            Company company = new Company();
            BeanUtils.copyProperties(group,company);
            System.out.println(company); // Company{compCode='10001', compAbbr='竑翼'}
        } catch (GenericBusinessException e) {
            e.printStackTrace();
        } catch (MappingException e) {
            e.printStackTrace();
        }
    }

    static class Company {
        @Column(name = "COMP_CODE")
        private String compCode;
        @Column(name = "COMP_ABBR")
        private String compAbbr;

        public String getCompCode() {
            return compCode;
        }

        public void setCompCode(String compCode) {
            this.compCode = compCode;
        }

        public String getCompAbbr() {
            return compAbbr;
        }

        public void setCompAbbr(String compAbbr) {
            this.compAbbr = compAbbr;
        }

        @Override
        public String toString() {
            return "Company{" +
                    "compCode='" + compCode + '\'' +
                    ", compAbbr='" + compAbbr + '\'' +
                    '}';
        }
    }

    static class Group{

        @FieldCheck(description = "集团编号不能为空!")
        @FieldMapping(field = "COMP_CODE")
        private String groupCode;

        @FieldCheck(description = "集团简称不能为空!")
        @FieldMapping(field = "COMP_ABBR")
        private String groupAbbr;

        public Group() {
        }

        public Group(String groupCode, String groupAbbr) {
            this.groupCode = groupCode;
            this.groupAbbr = groupAbbr;
        }

        public String getGroupCode() {
            return groupCode;
        }

        public void setGroupCode(String groupCode) {
            this.groupCode = groupCode;
        }

        public String getGroupAbbr() {
            return groupAbbr;
        }

        public void setGroupAbbr(String groupAbbr) {
            this.groupAbbr = groupAbbr;
        }
    }
}


