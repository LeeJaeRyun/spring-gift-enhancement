package gift.wishlist;

import gift.item.entity.Item;
import gift.item.repository.ItemRepository;
import gift.member.entity.Member;
import gift.member.repository.MemberRepository;
import gift.wishlist.entity.Wishlist;
import gift.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("멤버와 아이템으로 위시리스트 존재 여부 확인")
    void existsByMemberAndItem() {
        // given
        Member member = memberRepository.save(new Member("testuser@example.com", "password1234"));
        Item item = itemRepository.save(new Item("testItem", 1000, "testUrl"));
        wishlistRepository.save(new Wishlist(member, item));

        // when
        boolean exists = wishlistRepository.existsByMemberAndItem(member, item);

        // then
        assertThat(exists).isTrue();

        // then2 위시리스트 없는 경우
        Member otherMember = memberRepository.save(new Member("otheruser@example.com", "password456"));
        boolean notExists = wishlistRepository.existsByMemberAndItem(otherMember, item);
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("한 멤버의 위시리스트 조회")
    void findByMember() {
        // given
        Member member = memberRepository.save(new Member("findmember@example.com", "password789"));
        Item item1 = itemRepository.save(new Item("item1", 500, "url1"));
        Item item2 = itemRepository.save(new Item("item2", 1500, "url2"));

        Wishlist wishlist1 = new Wishlist(member, item1);
        Wishlist wishlist2 = new Wishlist(member, item2);

        wishlistRepository.save(wishlist1);
        wishlistRepository.save(wishlist2);

        // when
        List<Wishlist> wishlistList = wishlistRepository.findByMember(member);

        // then
        assertThat(wishlistList).hasSize(2)
                .extracting(w -> w.getItem().getName())
                .containsExactlyInAnyOrder("item1", "item2");
    }

    @Test
    @DisplayName("멤버와 아이템으로 위시리스트 삭제")
    void deleteByMemberAndItem() {
        // given
        Member member = new Member("test@example.com", "password123");
        memberRepository.save(member);

        Item item = new Item("TestItem", 1000, "http://image.com/test.jpg");
        itemRepository.save(item);

        Wishlist wishlist = new Wishlist(member, item);
        wishlistRepository.save(wishlist);

        // when
        wishlistRepository.deleteByMemberAndItem(member, item);

        // then
        boolean exists = wishlistRepository.existsByMemberAndItem(member, item);
        assertThat(exists).isFalse();
    }

}
