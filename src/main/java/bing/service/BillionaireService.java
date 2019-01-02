package bing.service;

import bing.common.Page;
import bing.model.Billionaire;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Billionaire Query Service
 *
 * @author: IceWee
 * @date: 2019.1.2
 */
public final class BillionaireService {

    private static class BillionaireServiceHolder {
        private static final BillionaireService INSTANCE = new BillionaireService();
    }

    /**
     * TOP 50 Billionaires of the world.
     */
    private final List<Billionaire> BILLIONAIRES = Arrays.asList(
            new Billionaire("#1", "Jeff Bezos", "$112 B", 54, "Amazon", "United States"),
            new Billionaire("#2", "Bill Gates", "$90 B", 63, "Microsoft", "United States"),
            new Billionaire("#3", "Warren Buffett", "$84 B", 88, "Berkshire Hathaway", "United States"),
            new Billionaire("#4", "Bernard Arnault", "$72 B", 69, "LVMH", "France"),
            new Billionaire("#5", "Mark Zuckerberg", "$71 B", 34, "Facebook", "United States"),
            new Billionaire("#6", "Amancio Ortega", "$70 B", 82, "Zara", "Spain"),
            new Billionaire("#7", "Carlos Slim Helu", "$67.1 B", 78, "telecom", "Mexico"),
            new Billionaire("#8", "Charles Koch", "$60 B", 83, "Koch Industries", "United States"),
            new Billionaire("#8", "David Koch", "$60 B", 78, "Koch Industries", "United States"),
            new Billionaire("#10", "Larry Ellison", "$58.5 B", 74, "software", "United States"),
            new Billionaire("#11", "Michael Bloomberg", "$50 B", 76, "Bloomberg LP", "United States"),
            new Billionaire("#12", "Larry Page", "$48.8 B", 45, "Google", "United States"),
            new Billionaire("#13", "Sergey Brin", "$47.5 B", 45, "Google", "United States"),
            new Billionaire("#14", "Jim Walton", "$46.4 B", 70, "Walmart", "United States"),
            new Billionaire("#15", "S. Robson Walton", "$46.2 B", 74, "Walmart", "United States"),
            new Billionaire("#16", "Alice Walton", "$46 B", 69, "Walmart", "United States"),
            new Billionaire("#17", "Ma Huateng", "$45.3 B", 47, "internet media", "China"),
            new Billionaire("#18", "Francoise Bettencourt Meyers", "$42.2 B", 65, "L'Oreal", "France"),
            new Billionaire("#19", "Mukesh Ambani", "$40.1 B", 61, "petrochemicals, oil & gas", "India"),
            new Billionaire("#20", "Jack Ma", "$39 B", 54, "e-commerce", "China"),
            new Billionaire("#21", "Sheldon Adelson", "$38.5 B", 85, "casinos", "United States"),
            new Billionaire("#22", "Steve Ballmer", "$38.4 B", 62, "Microsoft", "United States"),
            new Billionaire("#23", "Li Ka-shing", "$34.9 B", 90, "diversified", "Hong Kong"),
            new Billionaire("#24", "Hui Ka Yan", "$30.3 B", 60, "real estate", "China"),
            new Billionaire("#24", "Lee Shau Kee", "$30.3 B", 90, "real estate", "Hong Kong"),
            new Billionaire("#26", "Wang Jianlin", "$30 B", 64, "real estate", "China"),
            new Billionaire("#27", "Beate Heister & Karl Albrecht Jr.", "$29.8 B", 67, "supermarkets", "Germany"),
            new Billionaire("#28", "Phil Knight", "$29.6 B", 80, "Nike", "United States"),
            new Billionaire("#29", "Jorge Paulo Lemann", "$27.4 B", 79, "beer", "Brazil"),
            new Billionaire("#30", "Francois Pinault", "$27 B", 82, "luxury goods", "France"),
            new Billionaire("#31", "Georg Schaeffler", "$25.3 B", 54, "auto parts", "Germany"),
            new Billionaire("#32", "Susanne Klatten", "$25 B", 56, "BMW, pharmaceuticals", "Germany"),
            new Billionaire("#32", "David Thomson", "$25 B", 61, "media", "Canada"),
            new Billionaire("#34", "Jacqueline Mars", "$23.6 B", 79, "candy, pet food", "United States"),
            new Billionaire("#34", "John Mars", "$23.6 B", 83, "candy, pet food", "United States"),
            new Billionaire("#36", "Joseph Safra", "$23.5 B", 80, "banking", "Brazil"),
            new Billionaire("#37", "Giovanni Ferrero", "$23 B", 54, "Nutella, chocolates", "Italy"),
            new Billionaire("#37", "Dietrich Mateschitz", "$23 B", 74, "Red Bull", "Austria"),
            new Billionaire("#39", "Michael Dell", "$22.7 B", 53, "Dell computers", "United States"),
            new Billionaire("#39", "Masayoshi Son", "$22.7 B", 61, "internet, telecom", "Japan"),
            new Billionaire("#41", "Serge Dassault", "$22.6 B", 93, "diversified", "France"),
            new Billionaire("#42", "Stefan Quandt", "$22 B", 52, "BMW", "Germany"),
            new Billionaire("#43", "Yang Huiyan", "$21.9 B", 37, "real estate", "China"),
            new Billionaire("#44", "Paul Allen", "$21.7 B", 65, "Microsoft, investments", "United States"),
            new Billionaire("#45", "Leonardo Del Vecchio", "$21.2 B", 83, "eyeglasses", "Italy"),
            new Billionaire("#46", "Dieter Schwarz", "$20.9 B", 79, "retail", "Germany"),
            new Billionaire("#47", "Thomas Peterffy", "$20.3 B", 74, "discount brokerage", "United States"),
            new Billionaire("#48", "Theo Albrecht, Jr.", "$20.2 B", 68, "Aldi, Trader Joe's", "Germany"),
            new Billionaire("#48", "Len Blavatnik", "$20.2 B", 61, "diversified", "United States"),
            new Billionaire("#50", "He Xiangjian", "$20.1 B", 76, "home appliances", "China"),
            new Billionaire("#50", "Lui Che Woo", "$20.1 B", 89, "casinos", "Hong Kong"));

    private BillionaireService() {
        super();
    }

    public static BillionaireService instance() {
        return BillionaireServiceHolder.INSTANCE;
    }

    /**
     * search by page.
     *
     * @param name
     * @param page
     */
    public void search(String name, Page<Billionaire> page) {
        int pageSize = page.getPageSize();
        List<Billionaire> matches = new ArrayList<>();
        int totalCount = 0;
        for (Billionaire billionaire : BILLIONAIRES) {
            if (StringUtils.containsIgnoreCase(billionaire.getName(), name)) {
                matches.add(billionaire);
                totalCount++;
            }
        }
        page.setTotalCount(totalCount);
        int fromIndex = page.getIndex();
        if (fromIndex > totalCount - 1) {
            page.setData(Collections.emptyList());
        } else {
            int toIndex = fromIndex + pageSize;
            if (toIndex > totalCount) {
                toIndex = totalCount;
            }
            List<Billionaire> data = matches.subList(fromIndex, toIndex);
            page.setData(data);
        }
    }

}
