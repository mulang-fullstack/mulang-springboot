package yoonsome.mulang.api.student.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MycourseDTO {
    private Long courseId;
    private String courseTitle;
    private String courseThumbnail;
    private String teacherName;
    private Long totalLectures;
    private Long viewedLectures;
    private Double progressPercentage;

    // JPQL new 키워드용 생성자 (필수!)
    public MycourseDTO(Long courseId, String courseTitle, String courseThumbnail,
                       String teacherName, Long totalLectures, Long viewedLectures) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseThumbnail = courseThumbnail;
        this.teacherName = teacherName;
        this.totalLectures = totalLectures;
        this.viewedLectures = viewedLectures;

        if(totalLectures != null && totalLectures > 0 && viewedLectures != null){
            this.progressPercentage = Math.round((viewedLectures*100.0)/totalLectures * 10.0)/10.0;
        } else{
            this.progressPercentage = 0.0;
        }
    }


}
