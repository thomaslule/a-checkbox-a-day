require 'test/unit'

class MonthTest < Test::Unit::TestCase
    def setup
        $driver.get($appli_url + '/clear')
        $driver.get($appli_url + '/month/205001')
    end

    def test_add_task
        add_item('some task')
        check_persisted {
            assert_equal('some task', $driver.find_element(:css, '.item .item-name').text, 'task not created')
            refute($driver.find_element(:css, '.item input[type="checkbox"]').attribute('checked'), 'task created in done state')
        }
    end

    def test_check_task
        add_item('some task')
        $driver.find_element(:css, '.item input[type="checkbox"]').click
        check_persisted { assert($driver.find_element(:css, '.item input[type="checkbox"]').attribute('checked'), 'done state not saved') }
        refute($driver.find_element(:css, '.item .cancel-item-button').displayed?)
        refute($driver.find_element(:css, '.item .move-item-button').displayed?)
    end

    def test_rename_item
        add_item('abc')
        span = $driver.find_element(:css, '.item .item-name')
        span.click
        $driver.action.send_keys(span, :arrow_right).send_keys(span, :arrow_right).send_keys(span, :arrow_right).send_keys(span, ' modified').send_keys(span, :return).perform
        check_persisted { assert_equal('abc modified', $driver.find_element(:css, '.item .item-name').text, 'item not renamed') }
    end

    def test_cancel_item
        add_item('some item')
        $driver.find_element(:css, '.item .cancel-item-button').click
        check_persisted { assert_has_class('cancelled', $driver.find_element(:css, '.item'), 'item not correctly cancelled') }
    end

    def test_add_event
        $driver.find_element(:css, '#new-item-type-selected').click
        $driver.find_element(:css, '#new-item-type-list a[data-type="event"]').click
        add_item('some event')
        check_persisted { assert_has_class('event', $driver.find_element(:css, '.item'), 'event not saved') }
    end

    def test_add_note
        $driver.find_element(:css, '#new-item-type-selected').click
        $driver.find_element(:css, '#new-item-type-list a[data-type="note"]').click
        add_item('some event')
        check_persisted { assert_has_class('note', $driver.find_element(:css, '.item'), 'event not saved') }
    end

    def test_delete_item
        add_item('some item')
        $driver.find_element(:css, '.item .cancel-item-button').click
        $driver.find_element(:css, '.item .delete-item-button').click
        refute_empty($driver.find_elements(:css, '.item'))
        $driver.find_element(:css, '.bootbox-confirm .btn-primary').click
        check_persisted { give_time { assert_empty($driver.find_elements(:css, '.item'), 'item not correctly deleted') } }
    end

    def test_change_month
        $driver.get($appli_url + '/month/205504')
        assert_equal('avril 2055', $driver.find_element(:css, '#displayed-month').text, 'month not rightly displayed')
        add_item('some item')
        $driver.find_element(:css, '#previous-month').click
        assert_equal('mars 2055', $driver.find_element(:css, '#displayed-month').text, 'month not rightly displayed')
        assert_empty($driver.find_elements(:css, '.item'), 'item found in wrong month')
        $driver.find_element(:css, '#next-month').click
        assert_equal('some item', $driver.find_element(:css, '.item .item-name').text, 'item not saved in month')
    end

    def test_move_item
        add_item('some item')
        $driver.find_element(:css, '.item .move-item-button').click
        $driver.find_element(:css, 'a.move-button[data-destination-id="205002"]').click
        assert_has_class('moved', $driver.find_element(:css, '.item'), 'item not moved')
        $driver.find_element(:css, '#next-month').click
        assert_equal('some item', $driver.find_element(:css, '.item .item-name').text, 'new item not created')
    end

    def add_item(name)
        input = $driver.find_element(:css, '#new-item input[name="name"]')
        input.send_keys(name)
        input.submit
    end

    def assert_has_class(className, element, message)
        assert(element.attribute('class').split(/\s+/).include?(className), message)
    end

    def check_persisted
        yield
        $driver.navigate.refresh
        yield
    end

    def give_time
        wait = Selenium::WebDriver::Wait.new(:timeout => 2)
        e = nil
        begin
            wait.until {
                success = nil
                begin
                    yield
                    success = true
                rescue Exception => e
                    success = false
                end
                success
            }
        rescue Exception => f
            raise e
        end
    end
end
