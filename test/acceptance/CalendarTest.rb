require_relative 'AcadTest'

class MonthTest < AcadTest
    def setup
        $driver.get($appli_url + '/clear')
        $driver.get($appli_url + '/month/205001')
    end

    def test_edit_day
        edit_day('modified')
        edit_day('remodified')
    end

    def edit_day(text)
        day = $driver.find_element(:css, '.day[data-id="2050-01-05"] .day-text')
        day.clear;
        day.click
        $driver.action.send_keys(day, text).send_keys(day, :return).perform
        check_persisted { assert_equal(text, $driver.find_element(:css, '.day[data-id="2050-01-05"] .day-text').text, 'calendar not saved') }
    end
end
