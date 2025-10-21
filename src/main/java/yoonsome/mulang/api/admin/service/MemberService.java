//package yoonsome.mulang.admin.service;
//
//import yoonsome.mulang.admin.dto.MemberDTO;
//import yoonsome.mulang.admin.dto.PageDTO;
//import yoonsome.mulang.admin.entity.Member;
//import yoonsome.mulang.admin.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * 회원 관리 서비스
// */
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//
//    /**
//     * 회원 목록 조회 (페이징)
//     */
//    public PageDTO<MemberDTO> getMemberList(int page, int size, Map<String, Object> filters) {
//        // 정렬 조건 설정
//        Sort sort = createSort(filters.get("sort"));
//
//        // 페이지 요청 객체 생성 (페이지는 0부터 시작)
//        Pageable pageable = PageRequest.of(page - 1, size, sort);
//
//        // 필터링된 데이터 조회
//        Page<Member> memberPage = memberRepository.findByFilters(filters, pageable);
//
//        // DTO 변환
//        List<MemberDTO> memberDTOs = memberPage.getContent().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//
//        // PageDTO 생성
//        return PageDTO.<MemberDTO>builder()
//                .content(memberDTOs)
//                .currentPage(page)
//                .totalPages(memberPage.getTotalPages())
//                .totalCount(memberPage.getTotalElements())
//                .size(size)
//                .hasNext(memberPage.hasNext())
//                .hasPrevious(memberPage.hasPrevious())
//                .build();
//    }
//
//    /**
//     * 회원 상태 수정
//     */
//    @Transactional
//    public boolean updateMemberStatus(Long memberId, String status) {
//        try {
//            Member member = memberRepository.findById(memberId)
//                    .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
//
//            member.setActive("ACTIVE".equals(status));
//            memberRepository.save(member);
//
//            log.info("회원 상태 수정 완료: memberId={}, status={}", memberId, status);
//            return true;
//
//        } catch (Exception e) {
//            log.error("회원 상태 수정 실패: memberId={}, error={}", memberId, e.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * 회원 삭제
//     */
//    @Transactional
//    public boolean deleteMember(Long memberId) {
//        try {
//            if (!memberRepository.existsById(memberId)) {
//                throw new RuntimeException("회원을 찾을 수 없습니다.");
//            }
//
//            memberRepository.deleteById(memberId);
//            log.info("회원 삭제 완료: memberId={}", memberId);
//            return true;
//
//        } catch (Exception e) {
//            log.error("회원 삭제 실패: memberId={}, error={}", memberId, e.getMessage());
//            return false;
//        }
//    }
//
//    /**
//     * 정렬 조건 생성
//     */
//    private Sort createSort(Object sortParam) {
//        String sortType = sortParam != null ? sortParam.toString() : "LATEST";
//
//        switch (sortType) {
//            case "OLDEST":
//                return Sort.by(Sort.Direction.ASC, "createdAt");
//            case "NAME_ASC":
//                return Sort.by(Sort.Direction.ASC, "name");
//            case "NAME_DESC":
//                return Sort.by(Sort.Direction.DESC, "name");
//            case "LATEST":
//            default:
//                return Sort.by(Sort.Direction.DESC, "createdAt");
//        }
//    }
//
//    /**
//     * Entity를 DTO로 변환
//     */
//    private MemberDTO convertToDTO(Member member) {
//        return MemberDTO.builder()
//                .id(member.getId())
//                .name(member.getName())
//                .email(member.getEmail())
//                .role(member.getRole())
//                .active(member.isActive())
//                .createdAt(member.getCreatedAt())
//                .updatedAt(member.getUpdatedAt())
//                .build();
//    }
//}