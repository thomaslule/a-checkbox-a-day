require_relative 'AcadTest'

class MonthTest < AcadTest
    def setup
        $driver.get($appli_url + '/clear')
        $driver.get($appli_url + '/month/205001')
    end

    def test_edit_day
        day = $driver.find_element(:css, '.day[data-id="2050-01-05"] .day-text')
        day.click
        $driver.action.send_keys(day, 'modified').send_keys(day, :return).perform
        check_persisted { assert_equal('modified', $driver.find_element(:css, '.day[data-id="2050-01-05"] .day-text').text, 'calendar not saved') }
    end
end
