package gift.wishlist.repository;

import gift.member.entity.Member;
import gift.item.entity.Item;
import gift.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByMember(Member member);

    boolean existByMemberAndItem(Member member, Item item);

    void deleteByMemberAndItem(Member member, Item item);
}