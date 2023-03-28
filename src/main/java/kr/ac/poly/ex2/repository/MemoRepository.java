package kr.ac.poly.ex2.repository;

import jakarta.transaction.Transactional;
import kr.ac.poly.ex2.entity.Memo;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoRepository  extends JpaRepository<Memo, Long> {
    List<Memo>findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    Page<Memo> findAllByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteAllByMnoLessThan(Long from);

    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    @Transactional
    @Modifying
    @Query("UPDATE Memo m SET m.memoText = :memoText where m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    @Transactional
    @Modifying
    @Query("UPDATE Memo m SET m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
    int updateMemoText2(@Param("param") Memo memo);

    @Query(value = "select m from Memo m where m.mno > :mno",
    countQuery = "select count(m) from Memo m where m.mno >: mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);
}
